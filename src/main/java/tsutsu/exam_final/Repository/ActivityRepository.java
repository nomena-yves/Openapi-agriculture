package tsutsu.exam_final.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.Activity;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ActivityRepository {

    private final DatabaseConfig db;

    public ActivityRepository(DatabaseConfig db) {
        this.db = db;
    }

    public String save(String collectivityId, Activity activity) throws SQLException {
        String id = "ACT-" + System.currentTimeMillis();
        String sql = """
                INSERT INTO activities
                    (id, collectivity_id, label, description, date, type, mandatory)
                VALUES (?, ?, ?, ?, ?::date, ?, ?)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, collectivityId);
            ps.setString(3, activity.getLabel());
            ps.setString(4, activity.getDescription());
            ps.setDate(5, Date.valueOf(activity.getDate()));
            ps.setString(6, activity.getType().name());
            ps.setBoolean(7, activity.isMandatory());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public List<Activity> findByCollectivityId(String collectivityId) throws SQLException {
        String sql = """
                SELECT id, label, description, date, type, mandatory
                FROM activities
                WHERE collectivity_id = ?
                ORDER BY date DESC
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);

            List<Activity> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(mapRow(rs));
            }
            return result;
        }
    }

    public Optional<Activity> findById(String activityId) throws SQLException {
        String sql = """
                SELECT id, label, description, date, type, mandatory
                FROM activities WHERE id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }
    }

    public boolean existsById(String id) throws SQLException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT 1 FROM activities WHERE id = ?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    // Vérifie si la présence a déjà été faite pour un membre dans cette activité
    public boolean attendanceAlreadyRecorded(String activityId, String memberId)
            throws SQLException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT 1 FROM attendance WHERE activity_id = ? AND member_id = ?")) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public void saveAttendance(String activityId, String memberId,
                                boolean present, String excuseReason) throws SQLException {
        String sql = """
                INSERT INTO attendance (activity_id, member_id, present, excuse_reason)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (activity_id, member_id) DO NOTHING
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            ps.setBoolean(3, present);
            ps.setString(4, excuseReason);
            ps.executeUpdate();
        }
    }

    public List<AttendanceRecord> findAttendanceByActivity(String activityId)
            throws SQLException {
        String sql = """
                SELECT a.activity_id, a.member_id, a.present, a.excuse_reason,
                       m.first_name, m.last_name
                FROM attendance a
                INNER JOIN members m ON m.id = a.member_id
                WHERE a.activity_id = ?
                ORDER BY m.last_name
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, activityId);
            List<AttendanceRecord> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new AttendanceRecord(
                            rs.getString("member_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getBoolean("present"),
                            rs.getString("excuse_reason")
                    ));
                }
            }
            return result;
        }
    }

    // Taux d'assiduité d'un membre sur une période
    public double getAttendanceRate(String memberId, String collectivityId,
                                     LocalDate from, LocalDate to) throws SQLException {
        String sqlTotal = """
                SELECT COUNT(*) FROM activities
                WHERE collectivity_id = ? AND mandatory = true
                  AND date >= ? AND date <= ?
                """;
        String sqlPresent = """
                SELECT COUNT(*) FROM attendance att
                INNER JOIN activities ac ON ac.id = att.activity_id
                WHERE ac.collectivity_id = ? AND ac.mandatory = true
                  AND ac.date >= ? AND ac.date <= ?
                  AND att.member_id = ? AND att.present = true
                """;

        try (Connection conn = db.getConnection()) {
            long total;
            try (PreparedStatement ps = conn.prepareStatement(sqlTotal)) {
                ps.setString(1, collectivityId);
                ps.setDate(2, Date.valueOf(from));
                ps.setDate(3, Date.valueOf(to));
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    total = rs.getLong(1);
                }
            }
            if (total == 0) return 0.0;

            long present;
            try (PreparedStatement ps = conn.prepareStatement(sqlPresent)) {
                ps.setString(1, collectivityId);
                ps.setDate(2, Date.valueOf(from));
                ps.setDate(3, Date.valueOf(to));
                ps.setString(4, memberId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    present = rs.getLong(1);
                }
            }
            return Math.round((double) present / total * 10000.0) / 100.0;
        }
    }

    // Taux d'assiduité global d'une collectivité
    public double getCollectivityAttendanceRate(String collectivityId,
                                                 LocalDate from, LocalDate to) throws SQLException {
        String sql = """
                SELECT
                    COUNT(att.member_id) AS present_count,
                    (SELECT COUNT(*) FROM activities
                     WHERE collectivity_id = ? AND mandatory = true
                       AND date >= ? AND date <= ?) *
                    (SELECT COUNT(*) FROM member_collectivities WHERE collectivity_id = ?) AS total_expected
                FROM attendance att
                INNER JOIN activities ac ON ac.id = att.activity_id
                WHERE ac.collectivity_id = ? AND ac.mandatory = true
                  AND ac.date >= ? AND ac.date <= ?
                  AND att.present = true
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ps.setString(4, collectivityId);
            ps.setString(5, collectivityId);
            ps.setDate(6, Date.valueOf(from));
            ps.setDate(7, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                long presentCount = rs.getLong("present_count");
                long totalExpected = rs.getLong("total_expected");
                if (totalExpected == 0) return 0.0;
                return Math.round((double) presentCount / totalExpected * 10000.0) / 100.0;
            }
        }
    }

    private Activity mapRow(ResultSet rs) throws SQLException {
        return Activity.builder()
                .id(rs.getString("id"))
                .label(rs.getString("label"))
                .description(rs.getString("description"))
                .date(rs.getDate("date").toLocalDate())
                .type(Activity.ActivityType.valueOf(rs.getString("type")))
                .mandatory(rs.getBoolean("mandatory"))
                .build();
    }

    public record AttendanceRecord(
            String memberId, String firstName, String lastName,
            boolean present, String excuseReason
    ) {}
}
