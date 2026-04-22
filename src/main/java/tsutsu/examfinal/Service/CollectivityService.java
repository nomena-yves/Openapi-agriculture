package tsutsu.examfinal.Service;


import org.springframework.stereotype.Service;
import tsutsu.examfinal.DTO.CreateCollectivityDTO;
import tsutsu.examfinal.Entity.CollectivityEntity;
import tsutsu.examfinal.Entity.CollectivityStructureEntity;
import tsutsu.examfinal.Entity.MembreEntity;
import tsutsu.examfinal.Repository.CollectivityRepository;
import tsutsu.examfinal.Repository.MemberRepository;
import tsutsu.examfinal.exception.BadRequestException;
import tsutsu.examfinal.exception.NotFoundException;

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

    private CollectivityEntity createSingleCollectivity(CreateCollectivityDTO dto) {
        try {
            if (dto.getFederationApproval() == null || !dto.getFederationApproval()) {
                throw new BadRequestException(
                        "Federation approval is required to open a new collectivity.");
            }
            if (dto.getStructure() == null) {
                throw new BadRequestException(
                        "Collectivity structure (president, vicePresident, treasurer, secretary) is required.");
            }
            List<String> memberIds = dto.getMembers();
            if (memberIds == null || memberIds.size() < 10) {
                throw new BadRequestException(
                        "At least 10 members are required to open a collectivity. " +
                                "Provided: " + (memberIds == null ? 0 : memberIds.size()));
            }
            List<MembreEntity> members = memberRepository.findByIds(memberIds);
            if (members.size() != memberIds.size()) {
                throw new NotFoundException(
                        "One or more members not found.");
            }
            java.time.LocalDate sixMonthsAgo = java.time.LocalDate.now().minusMonths(6);
            long seniorEnough = members.stream()
                    .filter(m -> m.getMembershipDate().isBefore(sixMonthsAgo))
                    .count();

            if (seniorEnough < 5) {
                throw new BadRequestException(
                        "At least 5 members must have more than 6 months of seniority. " +
                                "Found: " + seniorEnough);
            }
            String presidentId     = dto.getStructure().getPresident();
            String vicePresidentId = dto.getStructure().getVicePresident();
            String treasurerId     = dto.getStructure().getTreasurer();
            String secretaryId     = dto.getStructure().getSecretary();

            MembreEntity president     = memberRepository.findById(presidentId)
                    .orElseThrow(() -> new NotFoundException("President not found: " + presidentId));
            MembreEntity vicePresident = memberRepository.findById(vicePresidentId)
                    .orElseThrow(() -> new NotFoundException("Vice-president not found: " + vicePresidentId));
            MembreEntity treasurer     = memberRepository.findById(treasurerId)
                    .orElseThrow(() -> new NotFoundException("Treasurer not found: " + treasurerId));
            List<String> structureIds = List.of(presidentId, vicePresidentId, treasurerId, secretaryId);
            for (String sid : structureIds) {
                if (!memberIds.contains(sid)) {
                    throw new BadRequestException(
                            "Structure member " + sid + " must be included in the members list.");
                }
            }
            String collectivityId = collectivityRepository.save(
                    dto.getLocation(),
                    presidentId,
                    vicePresidentId,
                    treasurerId,
                    secretaryId
            );
            collectivityRepository.assignMembersToCollectivity(collectivityId, memberIds);

            CollectivityStructureEntity structure = CollectivityStructureEntity.builder()
                    .president(president)
                    .vice_president(vicePresident)
                    .tresurer(treasurer)
                    .secretary(secretary)
                    .build();

            return CollectivityEntity.builder()
                    .id(collectivityId)
                    .location(dto.getLocation())
                    .structure(structure)
                    .membre(members)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
