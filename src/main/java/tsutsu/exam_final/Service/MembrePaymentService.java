package tsutsu.exam_final.Service;


import org.springframework.stereotype.Service;
import tsutsu.exam_final.DTO.CreateMemberPaymentDto;
import tsutsu.exam_final.Entity.FinancialAccount;
import tsutsu.exam_final.Entity.MemberPayment;
import tsutsu.exam_final.Entity.MembershipFee;
import tsutsu.exam_final.Entity.MembreEntity;
import tsutsu.exam_final.Repository.*;
import tsutsu.exam_final.exception.BadRequestException;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberPaymentService {

    private final MemberPaymentRepository memberPaymentRepository;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final FinancialAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public MemberPaymentService(MemberPaymentRepository memberPaymentRepository,
                                MemberRepository memberRepository,
                                MembershipFeeRepository membershipFeeRepository,
                                FinancialAccountRepository accountRepository,
                                TransactionRepository transactionRepository) {
        this.memberPaymentRepository = memberPaymentRepository;
        this.memberRepository = memberRepository;
        this.membershipFeeRepository = membershipFeeRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<MemberPayment> createPayments(String memberId,
                                              List<CreateMemberPaymentDto> dtos) {
        try {

            MembreEntity member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException(
                            "Member not found: " + memberId));

            List<MemberPayment> result = new ArrayList<>();
            List<String> savedIds = new ArrayList<>();

            for (CreateMemberPaymentDto dto : dtos) {

                MembershipFee fee = membershipFeeRepository
                        .findById(dto.getMembershipFeeIdentifier())
                        .orElseThrow(() -> new NotFoundException(
                                "Membership fee not found: " + dto.getMembershipFeeIdentifier()));

                FinancialAccount account = accountRepository
                        .findById(dto.getAccountCreditedIdentifier())
                        .orElseThrow(() -> new NotFoundException(
                                "Financial account not found: " + dto.getAccountCreditedIdentifier()));

                if (dto.getAmount() == null || dto.getAmount() <= 0) {
                    throw new BadRequestException(
                            "Payment amount must be greater than 0.");
                }


                if (dto.getPaymentMode() == null) {
                    throw new BadRequestException(
                            "Payment mode is required.");
                }

                String paymentId = memberPaymentRepository.save(
                        memberId,
                        fee.getId(),
                        account.getId(),
                        dto.getAmount(),
                        dto.getPaymentMode()
                );
                savedIds.add(paymentId);

                transactionRepository.save(
                        fee.getCollectivityId(),
                        memberId,
                        account.getId(),
                        dto.getAmount(),
                        dto.getPaymentMode()
                );


                accountRepository.addToAmount(account.getId(), dto.getAmount());

                account.setAmount(account.getAmount() + dto.getAmount());
                result.add(MemberPayment.builder()
                        .id(paymentId)
                        .amount(dto.getAmount())
                        .paymentMode(dto.getPaymentMode())
                        .accountCredited(account)
                        .creationDate(java.time.LocalDate.now())
                        .build());
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
