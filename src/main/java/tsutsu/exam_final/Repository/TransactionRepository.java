package tsutsu.exam_final.Repository;


import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.PaymentMode;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    private final DatabaseConfig db;

    public TransactionRepository(DatabaseConfig db) {
        this.db = db;
    }

    public String save(String collectivityId,
                       String memberId,
                       String accountId,
                       double amount,
                       PaymentMode paymentMode) throws SQLException {

        String sql = """
                INSERT INTO collectivity_transactions
                    (collectivity_id, member_id, account_id,
                     amount, payment_mode, creation_date)
                VALUES (?, ?, ?, ?, ?::payment_mode_type, CURRENT_DATE)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setString(2, memberId);
            ps.setString(3, accountId);
            ps.setDouble(4, amount);
            ps.setString(5, paymentMode.name());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }


    public List<RawTransaction> findByCollectivityAndPeriod(String collectivityId,
                                                            LocalDate from,
                                                            LocalDate to) throws SQLException {
        String sql = """
                SELECT id, collectivity_id, member_id, account_id,
                       amount, payment_mode, creation_date
                FROM collectivity_transactions
                WHERE collectivity_id = ?
                  AND creation_date >= ?
                  AND creation_date <= ?
                ORDER BY creation_date DESC
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            List<RawTransaction> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRaw(rs));
                }
            }
            return result;
        }
    }

    private RawTransaction mapRaw(ResultSet rs) throws SQLException {
        return new RawTransaction(
                rs.getString("id"),
                rs.getDate("creation_date").toLocalDate(),
                rs.getDouble("amount"),
                PaymentMode.valueOf(rs.getString("payment_mode")),
                rs.getString("account_id"),
                rs.getString("member_id")
        );
    }

    public record RawTransaction(
            String id,
            LocalDate creationDate,
            double amount,
            PaymentMode paymentMode,
            String accountId,
            String memberId
    ) {}
}
