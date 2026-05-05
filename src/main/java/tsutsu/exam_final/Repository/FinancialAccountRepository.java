package tsutsu.exam_final.Repository;


import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.Bank;
import tsutsu.exam_final.Entity.FinancialAccount;
import tsutsu.exam_final.Entity.MobileBankingService;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class FinancialAccountRepository {

    private final DatabaseConfig db;

    public FinancialAccountRepository(DatabaseConfig db) {
        this.db = db;
    }


    public List<FinancialAccount> findByCollectivityId(String collectivityId)
            throws SQLException {
        String sql = """
                SELECT id, account_type, amount, holder_name,
                       mobile_service, mobile_number,
                       bank_name, bank_code, bank_branch_code,
                       bank_account_number, bank_account_key
                FROM financial_accounts
                WHERE collectivity_id = ?
                ORDER BY account_type, created_at
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);

            List<FinancialAccount> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }


    public List<FinancialAccount> findByCollectivityIdAtDate(String collectivityId,
                                                             LocalDate at)
            throws SQLException {
        String sql = """
                SELECT
                    fa.id,
                    fa.account_type,
                    fa.holder_name,
                    fa.mobile_service,
                    fa.mobile_number,
                    fa.bank_name,
                    fa.bank_code,
                    fa.bank_branch_code,
                    fa.bank_account_number,
                    fa.bank_account_key,
                    COALESCE(SUM(ct.amount), 0) AS amount
                FROM financial_accounts fa
                LEFT JOIN collectivity_transactions ct
                    ON ct.account_id = fa.id
                    AND ct.creation_date <= ?
                WHERE fa.collectivity_id = ?
                GROUP BY fa.id, fa.account_type, fa.holder_name,
                         fa.mobile_service, fa.mobile_number,
                         fa.bank_name, fa.bank_code, fa.bank_branch_code,
                         fa.bank_account_number, fa.bank_account_key
                ORDER BY fa.account_type, fa.created_at
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(at));
            ps.setString(2, collectivityId);

            List<FinancialAccount> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }

    public Optional<FinancialAccount> findById(String id) throws SQLException {
        String sql = """
                SELECT id, account_type, amount, holder_name,
                       mobile_service, mobile_number,
                       bank_name, bank_code, bank_branch_code,
                       bank_account_number, bank_account_key
                FROM financial_accounts
                WHERE id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    public boolean existsById(String id) throws SQLException {
        String sql = "SELECT 1 FROM financial_accounts WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    public void addToAmount(String accountId, double amountToAdd) throws SQLException {
        String sql = """
                UPDATE financial_accounts
                SET amount = amount + ?
                WHERE id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amountToAdd);
            ps.setString(2, accountId);
            ps.executeUpdate();
        }
    }

    private FinancialAccount mapRow(ResultSet rs) throws SQLException {
        String typeStr = rs.getString("account_type");
        FinancialAccount.AccountType accountType =
                FinancialAccount.AccountType.valueOf(typeStr);

        FinancialAccount.FinancialAccountBuilder builder = FinancialAccount.builder()
                .id(rs.getString("id"))
                .amount(rs.getDouble("amount"))
                .accountType(accountType);

        switch (accountType) {
            case MOBILE_BANKING -> {
                builder.holderName(rs.getString("holder_name"));
                String mobileService = rs.getString("mobile_service");
                if (mobileService != null) {
                    builder.mobileBankingService(MobileBankingService.valueOf(mobileService));
                }
                builder.mobileNumber(rs.getLong("mobile_number"));
            }
            case BANK -> {
                builder.holderName(rs.getString("holder_name"));
                String bankNameStr = rs.getString("bank_name");
                if (bankNameStr != null) {
                    builder.bankName(Bank.valueOf(bankNameStr));
                }
                builder.bankCode(rs.getInt("bank_code"));
                builder.bankBranchCode(rs.getInt("bank_branch_code"));
                builder.bankAccountNumber(rs.getLong("bank_account_number"));
                builder.bankAccountKey(rs.getInt("bank_account_key"));
            }

        }

        return builder.build();
    }
}
