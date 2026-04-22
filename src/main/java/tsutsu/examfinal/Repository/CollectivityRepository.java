package tsutsu.examfinal.Repository;



import org.springframework.stereotype.Repository;
import tsutsu.examfinal.config.DatabaseConfig;

import java.sql.*;
import java.util.List;
import java.util.Optional;


@Repository
public class CollectivityRepository {

    private final DatabaseConfig db;

    public CollectivityRepository(DatabaseConfig db) {
        this.db = db;
    }


    public String save(String location,
                       String presidentId,
                       String vicePresidentId,
                       String treasurerId,
                       String secretaryId) throws SQLException {

        String sql = """
                INSERT INTO collectivities
                    (location, president_id, vice_president_id, treasurer_id, secretary_id)
                VALUES (?, ?::uuid, ?::uuid, ?::uuid, ?::uuid)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, location);
            ps.setString(2, presidentId);
            ps.setString(3, vicePresidentId);
            ps.setString(4, treasurerId);
            ps.setString(5, secretaryId);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public void assignMembersToCollectivity(String collectivityId,
                                            List<String> memberIds) throws SQLException {
        String sql = "UPDATE members SET collectivity_id = ?::uuid WHERE id = ?::uuid";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String memberId : memberIds) {
                ps.setString(1, collectivityId);
                ps.setString(2, memberId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public Optional<RawCollectivity> findRawById(String id) throws SQLException {
        String sql = """
                SELECT id, number, name, location,
                       president_id, vice_president_id, treasurer_id, secretary_id
                FROM collectivities
                WHERE id = ?::uuid
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
                SELECT id, number, name, location,
                       president_id, vice_president_id, treasurer_id, secretary_id
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
                SELECT id, number, name, location,
                       president_id, vice_president_id, treasurer_id, secretary_id
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
                WHERE id = ?::uuid
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
        String sql = "SELECT 1 FROM collectivities WHERE id = ?::uuid";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    private RawCollectivity mapRaw(ResultSet rs) throws SQLException {
        return new RawCollectivity(
                rs.getString("id"),
                rs.getString("number"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getString("president_id"),
                rs.getString("vice_president_id"),
                rs.getString("treasurer_id"),
                rs.getString("secretary_id")
        );
    }


    public record RawCollectivity(
            String id,
            String number,
            String name,
            String location,
            String presidentId,
            String vicePresidentId,
            String treasurerId,
            String secretaryId
    ) {}
}
