package tsutsu.exam_final.Service;


import org.springframework.stereotype.Service;
import tsutsu.exam_final.Entity.FinancialAccount;
import tsutsu.exam_final.Repository.CollectivityRepository;
import tsutsu.exam_final.Repository.FinancialAccountRepository;
import tsutsu.exam_final.exception.BadRequestException;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


@Service
public class FinancialAccountService {

    private final FinancialAccountRepository accountRepository;
    private final CollectivityRepository collectivityRepository;

    public FinancialAccountService(FinancialAccountRepository accountRepository,
                                   CollectivityRepository collectivityRepository) {
        this.accountRepository = accountRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<FinancialAccount> getAccounts(String collectivityId, LocalDate at) {
        try {

            if (!collectivityRepository.existsById(collectivityId)) {
                throw new NotFoundException("Collectivity not found: " + collectivityId);
            }

            if (at != null) {

                if (at.isAfter(LocalDate.now())) {
                    throw new BadRequestException(
                            "'at' date cannot be in the future.");
                }
                return accountRepository.findByCollectivityIdAtDate(collectivityId, at);
            }

            return accountRepository.findByCollectivityId(collectivityId);

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
