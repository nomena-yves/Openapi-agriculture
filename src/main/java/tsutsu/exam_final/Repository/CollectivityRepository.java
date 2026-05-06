package tsutsu.exam_final.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CollectivityRepository {

    private final DatabaseConfig db;

    public CollectivityRepository(DatabaseConfig db) {
        this.db = db;
    }

    public String save(String location) throws SQLException {
        String newId = "COL-" + System.currentTimeMillis();
        String sql = """
                INSERT INTO collectivities (id, location)
                VALUES (?, ?)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newId);
            ps.setString(2, location);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public void assignMembersToCollectivity(String collectivityId,
                                            List<String> memberIds,
                                            String defaultOccupation) throws SQLException {
        String sql = """
                INSERT INTO member_collectivities (member_id, collectivity_id, occupation)
                VALUES (?, ?, ?::occupation_type)
                ON CONFLICT DO NOTHING
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String memberId : memberIds) {
                ps.setString(1, memberId);
                ps.setString(2, collectivityId);
                ps.setString(3, defaultOccupation);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // Insère un membre avec une occupation spécifique dans member_collectivities
    public void assignMemberWithOccupation(String collectivityId,
                                           String memberId,
                                           String occupation) throws SQLException {
        String sql = """
                INSERT INTO member_collectivities (member_id, collectivity_id, occupation)
                VALUES (?, ?, ?::occupation_type)
                ON CONFLICT (member_id, collectivity_id) DO UPDATE SET occupation = EXCLUDED.occupation
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setString(3, occupation);
            ps.executeUpdate();
        }
    }

    public Optional<RawCollectivity> findRawById(String id) throws SQLException {
        String sql = """
                SELECT id, number, name, location, specialization
                FROM collectivities
                WHERE id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRaw(rs));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<RawCollectivity> findByName(String name) throws SQLException {
        String sql = """
                SELECT id, number, name, location, specialization
                FROM collectivities
                WHERE LOWER(name) = LOWER(?)
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRaw(rs));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<RawCollectivity> findByNumber(String number) throws SQLException {
        String sql = """
                SELECT id, number, name, location, specialization
                FROM collectivities
                WHERE number = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, number);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRaw(rs));
                }
                return Optional.empty();
            }
        }
    }

    public void assignIdentity(String collectivityId,
                               String number,
                               String name) throws SQLException {
        String sql = """
                UPDATE collectivities
                SET number = CASE WHEN number IS NULL THEN ? ELSE number END,
                    name   = CASE WHEN name   IS NULL THEN ? ELSE name   END
                WHERE id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, number);
            ps.setString(2, name);
            ps.setString(3, collectivityId);
            ps.executeUpdate();
        }
    }

    public boolean existsById(String id) throws SQLException {
        String sql = "SELECT 1 FROM collectivities WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Récupère l'occupation d'un membre dans une collectivité
    public Optional<String> findOccupation(String memberId, String collectivityId) throws SQLException {
        String sql = """
                SELECT occupation FROM member_collectivities
                WHERE member_id = ? AND collectivity_id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(rs.getString("occupation"));
                return Optional.empty();
            }
        }
    }

    // Récupère les IDs des membres avec un rôle spécifique dans une collectivité
    public List<String> findMemberIdsByOccupation(String collectivityId,
                                                   String occupation) throws SQLException {
        String sql = """
                SELECT member_id FROM member_collectivities
                WHERE collectivity_id = ? AND occupation = ?::occupation_type
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setString(2, occupation);

            List<String> ids = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getString("member_id"));
            }
            return ids;
        }
    }

    private RawCollectivity mapRaw(ResultSet rs) throws SQLException {
        return new RawCollectivity(
                rs.getString("id"),
                rs.getString("number"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getString("specialization")
        );
    }

    public record RawCollectivity(
            String id,
            String number,
            String name,
            String location,
            String specialization
    ) {}
}
