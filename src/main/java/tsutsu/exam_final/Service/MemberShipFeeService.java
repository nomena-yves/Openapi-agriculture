package tsutsu.exam_final.Service;


import org.springframework.stereotype.Service;
import tsutsu.exam_final.DTO.CreateMembershipFeeDto;
import tsutsu.exam_final.Entity.ActivityStatus;
import tsutsu.exam_final.Entity.MembershipFee;
import tsutsu.exam_final.Repository.CollectivityRepository;
import tsutsu.exam_final.Repository.MembershipFeeRepository;
import tsutsu.exam_final.exception.BadRequestException;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipFeeService {

    private final MembershipFeeRepository membershipFeeRepository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository membershipFeeRepository,
                                CollectivityRepository collectivityRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
        this.collectivityRepository = collectivityRepository;
    }


    public List<MembershipFee> getByCollectivity(String collectivityId) {
        try {

            if (!collectivityRepository.existsById(collectivityId)) {
                throw new NotFoundException("Collectivity not found: " + collectivityId);
            }

            return membershipFeeRepository.findByCollectivityId(collectivityId);

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public List<MembershipFee> create(String collectivityId,
                                      List<CreateMembershipFeeDto> dtos) {
        try {

            if (!collectivityRepository.existsById(collectivityId)) {
                throw new NotFoundException("Collectivity not found: " + collectivityId);
            }


            List<MembershipFee> fees = new ArrayList<>();
            for (CreateMembershipFeeDto dto : dtos) {


                if (dto.getFrequency() == null) {
                    throw new BadRequestException(
                            "Frequency is required and must be one of: WEEKLY, MONTHLY, ANNUALLY, PUNCTUALLY.");
                }


                if (dto.getAmount() == null || dto.getAmount() <= 0) {
                    throw new BadRequestException(
                            "Amount must be greater than 0. Provided: " + dto.getAmount());
                }

                fees.add(MembershipFee.builder()
                        .eligibleFrom(dto.getEligibleFrom())
                        .frequency(dto.getFrequency())
                        .amount(dto.getAmount())
                        .label(dto.getLabel())
                        .status(ActivityStatus.ACTIVE)
                        .collectivityId(collectivityId)
                        .build());
            }


            List<String> ids = membershipFeeRepository.saveAll(collectivityId, fees);


            List<MembershipFee> result = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                fees.get(i).setId(ids.get(i));
                result.add(fees.get(i));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
