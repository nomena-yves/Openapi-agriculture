package tsutsu.exam_final.Repository;


import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.ActivityStatus;
import tsutsu.exam_final.Entity.Frequency;
import tsutsu.exam_final.Entity.MembershipFee;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class MembershipFeeRepository {

    private final DatabaseConfig db;

    public MembershipFeeRepository(DatabaseConfig db) {
        this.db = db;
    }

    public List<String> saveAll(String collectivityId,
                                List<MembershipFee> fees) throws SQLException {
        String sql = """
                INSERT INTO membership_fees
                    (collectivity_id, eligible_from, frequency, amount, label, status)
                VALUES (?, ?, ?::frequency_type, ?, ?, ?::activity_status_type)
                RETURNING id
                """;

        List<String> ids = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (MembershipFee fee : fees) {
                ps.setString(1, collectivityId);
                ps.setDate(2, fee.getEligibleFrom() != null
                        ? Date.valueOf(fee.getEligibleFrom()) : null);
                ps.setString(3, fee.getFrequency().name());
                ps.setDouble(4, fee.getAmount());
                ps.setString(5, fee.getLabel());
                ps.setString(6, ActivityStatus.ACTIVE.name());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ids.add(rs.getString("id"));
                    }
                }
            }
        }
        return ids;
    }


    public List<MembershipFee> findByCollectivityId(String collectivityId) throws SQLException {
        String sql = """
                SELECT id, collectivity_id, eligible_from, frequency,
                       amount, label, status
                FROM membership_fees
                WHERE collectivity_id = ?
                ORDER BY eligible_from
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);

            List<MembershipFee> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }

    public Optional<MembershipFee> findById(String id) throws SQLException {
        String sql = """
                SELECT id, collectivity_id, eligible_from, frequency,
                       amount, label, status
                FROM membership_fees
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

    private MembershipFee mapRow(ResultSet rs) throws SQLException {
        Date eligibleFrom = rs.getDate("eligible_from");
        return MembershipFee.builder()
                .id(rs.getString("id"))
                .collectivityId(rs.getString("collectivity_id"))
                .eligibleFrom(eligibleFrom != null ? eligibleFrom.toLocalDate() : null)
                .frequency(Frequency.valueOf(rs.getString("frequency")))
                .amount(rs.getDouble("amount"))
                .label(rs.getString("label"))
                .status(ActivityStatus.valueOf(rs.getString("status")))
                .build();
    }
}
