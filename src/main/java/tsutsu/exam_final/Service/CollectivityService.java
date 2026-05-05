package tsutsu.exam_final.Service;

import org.springframework.stereotype.Service;
import tsutsu.exam_final.DTO.AssignIdentityDto;
import tsutsu.exam_final.DTO.CreateCollectivityDTO;
import tsutsu.exam_final.Entity.CollectivityEntity;
import tsutsu.exam_final.Entity.CollectivityStructureEntity;
import tsutsu.exam_final.Entity.MembreEntity;
import tsutsu.exam_final.Repository.CollectivityRepository;
import tsutsu.exam_final.Repository.MemberRepository;
import tsutsu.exam_final.exception.BadRequestException;
import tsutsu.exam_final.exception.ConflictException;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectivityService {

    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public CollectivityService(CollectivityRepository collectivityRepository,
                               MemberRepository memberRepository) {
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityEntity> createCollectivities(List<CreateCollectivityDTO> dtos) {
        List<CollectivityEntity> created = new ArrayList<>();
        for (CreateCollectivityDTO dto : dtos) {
            created.add(createSingleCollectivity(dto));
        }
        return created;
    }

    public CollectivityEntity getById(String collectivityId) {
        try {
            return buildCollectivityResponse(collectivityId);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public CollectivityEntity assignIdentity(String collectivityId, AssignIdentityDto dto) {
        try {
            CollectivityRepository.RawCollectivity raw = collectivityRepository
                    .findRawById(collectivityId)
                    .orElseThrow(() -> new NotFoundException(
                            "Collectivity not found: " + collectivityId));

            boolean hasNumber = dto.getNumber() != null;
            boolean hasName   = dto.getName() != null && !dto.getName().isBlank();

            if (!hasNumber && !hasName) {
                throw new BadRequestException("At least one of 'number' or 'name' must be provided.");
            }
            if (hasNumber && raw.number() != null) {
                throw new ConflictException("The number has already been assigned and cannot be changed.");
            }
            if (hasName && raw.name() != null) {
                throw new ConflictException("The name has already been assigned and cannot be changed.");
            }
            if (hasNumber && collectivityRepository.findByNumber(String.valueOf(dto.getNumber())).isPresent()) {
                throw new ConflictException("The number '" + dto.getNumber() + "' is already used.");
            }
            if (hasName && collectivityRepository.findByName(dto.getName()).isPresent()) {
                throw new ConflictException("The name '" + dto.getName() + "' is already used.");
            }

            collectivityRepository.assignIdentity(
                    collectivityId,
                    hasNumber ? String.valueOf(dto.getNumber()) : null,
                    hasName   ? dto.getName() : null
            );

            return buildCollectivityResponse(collectivityId);

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private CollectivityEntity createSingleCollectivity(CreateCollectivityDTO dto) {
        try {
            if (dto.getFederationApproval() == null || !dto.getFederationApproval()) {
                throw new BadRequestException("Federation approval is required.");
            }
            if (dto.getStructure() == null) {
                throw new BadRequestException("Collectivity structure is required.");
            }

            List<String> memberIds = dto.getMembers();
            if (memberIds == null || memberIds.size() < 10) {
                throw new BadRequestException(
                        "At least 10 members are required. Provided: " +
                        (memberIds == null ? 0 : memberIds.size()));
            }

            List<MembreEntity> members = memberRepository.findByIds(memberIds);
            if (members.size() != memberIds.size()) {
                throw new NotFoundException("One or more members not found.");
            }

            java.time.LocalDate sixMonthsAgo = java.time.LocalDate.now().minusMonths(6);
            long seniorEnough = members.stream()
                    .filter(m -> m.getMembershipDate().isBefore(sixMonthsAgo))
                    .count();
            if (seniorEnough < 5) {
                throw new BadRequestException(
                        "At least 5 members must have 6+ months seniority. Found: " + seniorEnough);
            }

            String presidentId     = dto.getStructure().getPresident();
            String vicePresidentId = dto.getStructure().getVicePresident();
            String treasurerId     = dto.getStructure().getTreasurer();
            String secretaryId     = dto.getStructure().getSecretary();

            for (String sid : List.of(presidentId, vicePresidentId, treasurerId, secretaryId)) {
                if (!memberIds.contains(sid)) {
                    throw new BadRequestException("Structure member " + sid + " must be in members list.");
                }
            }

            MembreEntity president     = memberRepository.findById(presidentId).orElseThrow();
            MembreEntity vicePresident = memberRepository.findById(vicePresidentId).orElseThrow();
            MembreEntity treasurer     = memberRepository.findById(treasurerId).orElseThrow();
            MembreEntity secretary     = memberRepository.findById(secretaryId).orElseThrow();

            // Créer la collectivité
            String collectivityId = collectivityRepository.save(dto.getLocation());

            // Insérer tous les membres comme JUNIOR par défaut
            collectivityRepository.assignMembersToCollectivity(collectivityId, memberIds, "JUNIOR");

            // Mettre à jour les occupations spécifiques
            collectivityRepository.assignMemberWithOccupation(collectivityId, presidentId,     "PRESIDENT");
            collectivityRepository.assignMemberWithOccupation(collectivityId, vicePresidentId, "VICE_PRESIDENT");
            collectivityRepository.assignMemberWithOccupation(collectivityId, treasurerId,     "TREASURER");
            collectivityRepository.assignMemberWithOccupation(collectivityId, secretaryId,     "SECRETARY");

            CollectivityStructureEntity structure = CollectivityStructureEntity.builder()
                    .president(president)
                    .vicePresident(vicePresident)
                    .treasurer(treasurer)
                    .secretary(secretary)
                    .build();

            return CollectivityEntity.builder()
                    .id(collectivityId)
                    .number(null)
                    .name(null)
                    .location(dto.getLocation())
                    .structure(structure)
                    .members(members)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private CollectivityEntity buildCollectivityResponse(String collectivityId) throws SQLException {
        CollectivityRepository.RawCollectivity raw = collectivityRepository
                .findRawById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        // Récupérer président, VP, trésorier, secrétaire via member_collectivities
        List<String> presidentIds     = collectivityRepository.findMemberIdsByOccupation(collectivityId, "PRESIDENT");
        List<String> vicePresidentIds = collectivityRepository.findMemberIdsByOccupation(collectivityId, "VICE_PRESIDENT");
        List<String> treasurerIds     = collectivityRepository.findMemberIdsByOccupation(collectivityId, "TREASURER");
        List<String> secretaryIds     = collectivityRepository.findMemberIdsByOccupation(collectivityId, "SECRETARY");

        MembreEntity president     = presidentIds.isEmpty()     ? null : memberRepository.findById(presidentIds.get(0)).orElse(null);
        MembreEntity vicePresident = vicePresidentIds.isEmpty() ? null : memberRepository.findById(vicePresidentIds.get(0)).orElse(null);
        MembreEntity treasurer     = treasurerIds.isEmpty()     ? null : memberRepository.findById(treasurerIds.get(0)).orElse(null);
        MembreEntity secretary     = secretaryIds.isEmpty()     ? null : memberRepository.findById(secretaryIds.get(0)).orElse(null);

        CollectivityStructureEntity structure = CollectivityStructureEntity.builder()
                .president(president)
                .vicePresident(vicePresident)
                .treasurer(treasurer)
                .secretary(secretary)
                .build();

        List<MembreEntity> members = memberRepository.findByCollectivityId(collectivityId);

        return CollectivityEntity.builder()
                .id(raw.id())
                .number(raw.number())
                .name(raw.name())
                .location(raw.location())
                .structure(structure)
                .members(members)
                .build();
    }
}
