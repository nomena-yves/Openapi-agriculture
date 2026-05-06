package tsutsu.exam_final.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsRepository {

    private final DatabaseConfig db;

    public StatisticsRepository(DatabaseConfig db) {
        this.db = db;
    }

    // Montant total payé par membre dans une collectivité sur une période
    public List<MemberPaymentStat> getMemberPaymentStats(String collectivityId,
                                                          LocalDate from,
                                                          LocalDate to) throws SQLException {
        String sql = """
                SELECT
                    m.id AS member_id,
                    m.first_name,
                    m.last_name,
                    COALESCE(SUM(mp.amount), 0) AS total_paid
                FROM members m
                INNER JOIN member_collectivities mc ON mc.member_id = m.id
                LEFT JOIN member_payments mp ON mp.member_id = m.id
                    AND mp.creation_date >= ?
                    AND mp.creation_date <= ?
                    AND mp.membership_fee_id IN (
                        SELECT id FROM membership_fees
                        WHERE collectivity_id = ? AND status = 'ACTIVE'
                    )
                WHERE mc.collectivity_id = ?
                GROUP BY m.id, m.first_name, m.last_name
                ORDER BY m.last_name, m.first_name
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            ps.setString(3, collectivityId);
            ps.setString(4, collectivityId);

            List<MemberPaymentStat> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new MemberPaymentStat(
                            rs.getString("member_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDouble("total_paid")
                    ));
                }
            }
            return result;
        }
    }

    // Montant total des cotisations actives attendues sur une période (pour calculer impayés)
    public double getExpectedAmountForPeriod(String collectivityId,
                                              LocalDate from,
                                              LocalDate to) throws SQLException {
        String sql = """
                SELECT COALESCE(SUM(amount), 0) AS total_expected
                FROM membership_fees
                WHERE collectivity_id = ?
                  AND status = 'ACTIVE'
                  AND (eligible_from IS NULL OR eligible_from <= ?)
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getDouble("total_expected");
            }
        }
    }

    // Liste de toutes les collectivités avec leurs stats fédération
    public List<CollectivityFedStat> getFederationStats(LocalDate from,
                                                         LocalDate to) throws SQLException {
        String sql = """
                SELECT
                    c.id AS collectivity_id,
                    c.name AS collectivity_name,
                    COUNT(DISTINCT mc.member_id) AS total_members,
                    COUNT(DISTINCT CASE WHEN m.membership_date >= ? THEN m.id END) AS new_members
                FROM collectivities c
                LEFT JOIN member_collectivities mc ON mc.collectivity_id = c.id
                LEFT JOIN members m ON m.id = mc.member_id
                GROUP BY c.id, c.name
                ORDER BY c.name
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(from));

            List<CollectivityFedStat> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new CollectivityFedStat(
                            rs.getString("collectivity_id"),
                            rs.getString("collectivity_name"),
                            rs.getInt("total_members"),
                            rs.getInt("new_members")
                    ));
                }
            }
            return result;
        }
    }

    // Nombre de membres à jour dans leurs cotisations actives pour une collectivité
    public int countMembersUpToDate(String collectivityId,
                                     LocalDate from,
                                     LocalDate to) throws SQLException {
        String sql = """
                SELECT COUNT(DISTINCT mc.member_id) AS up_to_date
                FROM member_collectivities mc
                WHERE mc.collectivity_id = ?
                  AND NOT EXISTS (
                      SELECT 1 FROM membership_fees mf
                      WHERE mf.collectivity_id = ?
                        AND mf.status = 'ACTIVE'
                        AND (mf.eligible_from IS NULL OR mf.eligible_from <= ?)
                        AND NOT EXISTS (
                            SELECT 1 FROM member_payments mp
                            WHERE mp.member_id = mc.member_id
                              AND mp.membership_fee_id = mf.id
                              AND mp.creation_date >= ?
                              AND mp.creation_date <= ?
                        )
                  )
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setString(2, collectivityId);
            ps.setDate(3, Date.valueOf(to));
            ps.setDate(4, Date.valueOf(from));
            ps.setDate(5, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("up_to_date");
            }
        }
    }

    public record MemberPaymentStat(
            String memberId,
            String firstName,
            String lastName,
            double totalPaid
    ) {}

    public record CollectivityFedStat(
            String collectivityId,
            String collectivityName,
            int totalMembers,
            int newMembers
    ) {}
}
