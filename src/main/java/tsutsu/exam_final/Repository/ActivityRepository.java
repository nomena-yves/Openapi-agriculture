package tsutsu.exam_final.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.Activity;
import tsutsu.exam_final.Entity.AttendanceStatus;
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
                    (id, collectivity_id, label, activity_type,
                     recurrence_week_ordinal, recurrence_day_of_week, executive_date)
                VALUES (?, ?, ?, ?, ?, ?, ?::date)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, collectivityId);
            ps.setString(3, activity.getLabel());
            ps.setString(4, activity.getActivityType().name());

            if (activity.getRecurrenceRule() != null) {
                ps.setInt(5, activity.getRecurrenceRule().getWeekOrdinal());
                ps.setString(6, activity.getRecurrenceRule().getDayOfWeek());
            } else {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.VARCHAR);
            }

            if (activity.getExecutiveDate() != null) {
                ps.setDate(7, Date.valueOf(activity.getExecutiveDate()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public List<Activity> findByCollectivityId(String collectivityId) throws SQLException {
        String sql = """
                SELECT id, label, activity_type,
                       recurrence_week_ordinal, recurrence_day_of_week, executive_date
                FROM activities
                WHERE collectivity_id = ?
                ORDER BY executive_date DESC NULLS LAST
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
                SELECT id, label, activity_type,
                       recurrence_week_ordinal, recurrence_day_of_week, executive_date
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

    // Vérifie si présence déjà confirmée (ATTENDED ou MISSING — pas UNDEFINED)
    public boolean attendanceAlreadyConfirmed(String activityId, String memberId)
            throws SQLException {
        String sql = """
                SELECT 1 FROM attendance
                WHERE activity_id = ? AND member_id = ?
                  AND attendance_status IN ('ATTENDED', 'MISSING')
                """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public void saveAttendance(String activityId, String memberId,
                                AttendanceStatus status) throws SQLException {
        String sql = """
                INSERT INTO attendance (id, activity_id, member_id, attendance_status)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (activity_id, member_id)
                DO UPDATE SET attendance_status = EXCLUDED.attendance_status
                WHERE attendance.attendance_status = 'UNDEFINED'
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "ATT-" + System.currentTimeMillis() + "-" + memberId);
            ps.setString(2, activityId);
            ps.setString(3, memberId);
            ps.setString(4, status.name());
            ps.executeUpdate();
        }
    }

    public List<AttendanceRecord> findAttendanceByActivity(String activityId)
            throws SQLException {
        String sql = """
                SELECT a.id, a.member_id, a.attendance_status,
                       m.first_name, m.last_name, m.email,
                       mc.occupation
                FROM attendance a
                INNER JOIN members m ON m.id = a.member_id
                LEFT JOIN member_collectivities mc ON mc.member_id = m.id
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
                            rs.getString("id"),
                            rs.getString("member_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("occupation"),
                            AttendanceStatus.valueOf(rs.getString("attendance_status"))
                    ));
                }
            }
            return result;
        }
    }

    // Taux d'assiduité d'un membre sur une période (Bonus 2)
    public double getAttendanceRate(String memberId, String collectivityId,
                                     LocalDate from, LocalDate to) throws SQLException {
        String sqlTotal = """
                SELECT COUNT(*) FROM activities
                WHERE collectivity_id = ?
                  AND executive_date >= ? AND executive_date <= ?
                """;
        String sqlPresent = """
                SELECT COUNT(*) FROM attendance att
                INNER JOIN activities ac ON ac.id = att.activity_id
                WHERE ac.collectivity_id = ?
                  AND ac.executive_date >= ? AND ac.executive_date <= ?
                  AND att.member_id = ?
                  AND att.attendance_status = 'ATTENDED'
                """;

        try (Connection conn = db.getConnection()) {
            long total;
            try (PreparedStatement ps = conn.prepareStatement(sqlTotal)) {
                ps.setString(1, collectivityId);
                ps.setDate(2, Date.valueOf(from));
                ps.setDate(3, Date.valueOf(to));
                try (ResultSet rs = ps.executeQuery()) { rs.next(); total = rs.getLong(1); }
            }
            if (total == 0) return 0.0;

            long present;
            try (PreparedStatement ps = conn.prepareStatement(sqlPresent)) {
                ps.setString(1, collectivityId);
                ps.setDate(2, Date.valueOf(from));
                ps.setDate(3, Date.valueOf(to));
                ps.setString(4, memberId);
                try (ResultSet rs = ps.executeQuery()) { rs.next(); present = rs.getLong(1); }
            }
            return Math.round((double) present / total * 10000.0) / 100.0;
        }
    }

    // Taux d'assiduité global d'une collectivité (Bonus 2)
    public double getCollectivityAttendanceRate(String collectivityId,
                                                 LocalDate from, LocalDate to) throws SQLException {
        String sql = """
                SELECT
                    COUNT(CASE WHEN att.attendance_status = 'ATTENDED' THEN 1 END) AS present_count,
                    COUNT(att.member_id) AS total_records
                FROM attendance att
                INNER JOIN activities ac ON ac.id = att.activity_id
                WHERE ac.collectivity_id = ?
                  AND ac.executive_date >= ? AND ac.executive_date <= ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                long present = rs.getLong("present_count");
                long total = rs.getLong("total_records");
                if (total == 0) return 0.0;
                return Math.round((double) present / total * 10000.0) / 100.0;
            }
        }
    }

    private Activity mapRow(ResultSet rs) throws SQLException {
        Activity.MonthlyRecurrenceRule rule = null;
        int weekOrdinal = rs.getInt("recurrence_week_ordinal");
        String dayOfWeek = rs.getString("recurrence_day_of_week");
        if (!rs.wasNull() && dayOfWeek != null) {
            rule = new Activity.MonthlyRecurrenceRule(weekOrdinal, dayOfWeek);
        }

        Date execDate = rs.getDate("executive_date");

        return Activity.builder()
                .id(rs.getString("id"))
                .label(rs.getString("label"))
                .activityType(Activity.ActivityType.valueOf(rs.getString("activity_type")))
                .recurrenceRule(rule)
                .executiveDate(execDate != null ? execDate.toLocalDate() : null)
                .build();
    }

    public record AttendanceRecord(
            String id, String memberId, String firstName, String lastName,
            String email, String occupation, AttendanceStatus status
    ) {}
}
