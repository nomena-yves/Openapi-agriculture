package mg.federation.api.service;


import org.springframework.stereotype.Service;
import tsutsu.examfinal.DTO.CreateMemberDTO;
import tsutsu.examfinal.Entity.MemberOccupationEntity;
import tsutsu.examfinal.Entity.MembreEntity;
import tsutsu.examfinal.Repository.CollectivityRepository;
import tsutsu.examfinal.Repository.MemberRepository;
import tsutsu.examfinal.exception.BadRequestException;
import tsutsu.examfinal.exception.NotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public MemberService(MemberRepository memberRepository,
                         CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembreEntity> createMembers(List<CreateMemberDTO> dtos) {
        List<MembreEntity> created = new ArrayList<>();

        for (CreateMemberDTO dto : dtos) {
            created.add(createSingleMember(dto));
        }

        return created;
    }
    private MembreEntity createSingleMember(CreateMemberDTO dto) {
        try {
            if (!collectivityRepository.existsById(dto.getCollectivityIdentifier())) {
                throw new NotFoundException(
                        "Collectivity not found: " + dto.getCollectivityIdentifier());
            }
            if (dto.getRegistrationFeePaid() == null || !dto.getRegistrationFeePaid()) {
                throw new BadRequestException(
                        "Registration fee (50 000 MGA) must be paid.");
            }
            if (dto.getMembershipDuesPaid() == null || !dto.getMembershipDuesPaid()) {
                throw new BadRequestException(
                        "Membership dues must be paid before joining.");
            }
            List<String> refereeIds = dto.getReferees();
            List<MembreEntity> referees = memberRepository.findByIds(refereeIds);

            if (referees.size() != refereeIds.size()) {
                throw new NotFoundException(
                        "One or more referees not found.");
            }
            boolean allSenior = referees.stream()
                    .allMatch(r -> r.getOccupation() == MemberOccupationEntity.SENIOR);
            if (!allSenior) {
                throw new BadRequestException(
                        "All referees must be confirmed members (SENIOR occupation).");
            }
            LocalDate ninetyDaysAgo = LocalDate.now().minusDays(90);
            boolean allAncient = referees.stream()
                    .allMatch(r -> r.getMembershipDate().isBefore(ninetyDaysAgo));
            if (!allAncient) {
                throw new BadRequestException(
                        "All referees must have more than 90 days of membership.");
            }

            String targetCollectivityId = dto.getCollectivityIdentifier();
            long fromTarget = referees.stream()
                    .filter(r -> targetCollectivityId.equals(r.getCollectivityId()))
                    .count();
            long fromOther = referees.size() - fromTarget;

            if (fromTarget < fromOther) {
                throw new BadRequestException(
                        "The number of referees from the target collectivity (" + fromTarget +
                                ") must be >= the number from other collectivities (" + fromOther + ").");
            }


            if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new BadRequestException(
                        "Email already exists: " + dto.getEmail());
            }


            MembreEntity member = MembreEntity.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .birthDate(dto.getBirthDate())
                    .gender(dto.getGender())
                    .adress(dto.getAddress())
                    .profession(dto.getProfession())
                    .phoneNumber(String.valueOf(dto.getPhoneNumber()))
                    .email(dto.getEmail())
                    .Occupation(dto.getOccupation())
                    .membershipDate(LocalDate.now())
                    .collectivity(targetCollectivityId)
                    .build();

            String newId = memberRepository.save(member);
            member.setId(newId);

            memberRepository.saveReferees(newId, refereeIds);
            member.setReferees(referees);

            return member;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
