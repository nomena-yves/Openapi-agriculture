package tsutsu.exam_final.Repository;


import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.PaymentMode;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberPaymentRepository {

    private final DatabaseConfig db;

    public MemberPaymentRepository(DatabaseConfig db) {
        this.db = db;
    }

    public String save(String memberId,
                       String membershipFeeId,
                       String accountId,
                       int amount,
                       PaymentMode paymentMode) throws SQLException {

        String sql = """
                INSERT INTO member_payments
                    (member_id, membership_fee_id, account_id,
                     amount, payment_mode, creation_date)
                VALUES (?::uuid, ?::uuid, ?::uuid, ?, ?::payment_mode_type, CURRENT_DATE)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, memberId);
            ps.setString(2, membershipFeeId);
            ps.setString(3, accountId);
            ps.setInt(4, amount);
            ps.setString(5, paymentMode.name());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public List<RawPayment> findRawByIds(List<String> ids) throws SQLException {
        if (ids == null || ids.isEmpty()) return List.of();

        String placeholders = ids.stream()
                .map(i -> "?::uuid")
                .reduce((a, b) -> a + ", " + b)
                .orElse("?::uuid");

        String sql = String.format("""
                SELECT id, member_id, membership_fee_id, account_id,
                       amount, payment_mode, creation_date
                FROM member_payments
                WHERE id IN (%s)
                """, placeholders);

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < ids.size(); i++) {
                ps.setString(i + 1, ids.get(i));
            }

            List<RawPayment> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRaw(rs));
                }
            }
            return result;
        }
    }

    private RawPayment mapRaw(ResultSet rs) throws SQLException {
        return new RawPayment(
                rs.getString("id"),
                rs.getInt("amount"),
                PaymentMode.valueOf(rs.getString("payment_mode")),
                rs.getString("account_id"),
                rs.getDate("creation_date").toLocalDate()
        );
    }

    public record RawPayment(
            String id,
            int amount,
            PaymentMode paymentMode,
            String accountId,
            java.time.LocalDate creationDate
    ) {}
}
