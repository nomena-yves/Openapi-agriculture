package tsutsu.exam_final.Service;


import org.springframework.stereotype.Service;
import tsutsu.exam_final.Entity.CollectivityTransaction;
import tsutsu.exam_final.Repository.CollectivityRepository;
import tsutsu.exam_final.Repository.FinancialAccountRepository;
import tsutsu.exam_final.Repository.MemberRepository;
import tsutsu.exam_final.Repository.TransactionRepository;
import tsutsu.exam_final.exception.BadRequestException;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;
    private final FinancialAccountRepository accountRepository;
    private final MemberRepository memberRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CollectivityRepository collectivityRepository,
                              FinancialAccountRepository accountRepository,
                              MemberRepository memberRepository) {
        this.transactionRepository = transactionRepository;
        this.collectivityRepository = collectivityRepository;
        this.accountRepository = accountRepository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityTransaction> getTransactions(String collectivityId,
                                                         LocalDate from,
                                                         LocalDate to) {
        try {

            if (!collectivityRepository.existsById(collectivityId)) {
                throw new NotFoundException("Collectivity not found: " + collectivityId);
            }


            if (from == null || to == null) {
                throw new BadRequestException(
                        "Query parameters 'from' and 'to' are mandatory.");
            }
            if (from.isAfter(to)) {
                throw new BadRequestException(
                        "'from' date must be before or equal to 'to' date.");
            }

            List<TransactionRepository.RawTransaction> raws =
                    transactionRepository.findByCollectivityAndPeriod(collectivityId, from, to);


            List<CollectivityTransaction> result = new ArrayList<>();
            for (TransactionRepository.RawTransaction raw : raws) {
                var account = accountRepository.findById(raw.accountId())
                        .orElse(null);
                var member = memberRepository.findById(raw.memberId())
                        .orElse(null);

                result.add(CollectivityTransaction.builder()
                        .id(raw.id())
                        .creationDate(raw.creationDate())
                        .amount(raw.amount())
                        .paymentMode(raw.paymentMode())
                        .accountCredited(account)
                        .memberDebited(member)
                        .build());
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
