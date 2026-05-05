package tsutsu.exam_final.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.exam_final.Entity.GenderEntity;
import tsutsu.exam_final.Entity.MemberOccupationEntity;
import tsutsu.exam_final.Entity.MembreEntity;
import tsutsu.exam_final.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final DatabaseConfig db;

    public MemberRepository(DatabaseConfig db) {
        this.db = db;
    }
    public String save(MembreEntity member) throws SQLException {
        String sql = """
                INSERT INTO members
                    (id, first_name, last_name, birth_date, gender, address,
                     profession, phone_number, email, membership_date)
                VALUES (?, ?, ?, ?::date, ?::gender_type, ?, ?, ?, ?, ?::date)
                RETURNING id
                """;

        String newId = "M-" + System.currentTimeMillis();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newId);
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getLastName());
            ps.setDate(4, Date.valueOf(member.getBirthDate()));
            ps.setString(5, member.getGender().name());
            ps.setString(6, member.getAddress());
            ps.setString(7, member.getProfession());
            ps.setLong(8, Long.parseLong(member.getPhoneNumber()));
            ps.setString(9, member.getEmail());
            ps.setDate(10, Date.valueOf(member.getMembershipDate()));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public void saveMemberCollectivity(String memberId,
                                       String collectivityId,
                                       String occupation) throws SQLException {
        String sql = """
                INSERT INTO member_collectivities (member_id, collectivity_id, occupation)
                VALUES (?, ?, ?::occupation_type)
                ON CONFLICT DO NOTHING
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setString(3, occupation);
            ps.executeUpdate();
        }
    }

    public void saveReferees(String memberId, List<String> refereeIds) throws SQLException {
        String sql = "INSERT INTO member_referees (member_id, referee_id) VALUES (?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String refereeId : refereeIds) {
                ps.setString(1, memberId);
                ps.setString(2, refereeId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public Optional<MembreEntity> findById(String id) throws SQLException {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email,
                       m.membership_date,
                       mc.occupation, mc.collectivity_id
                FROM members m
                LEFT JOIN member_collectivities mc ON mc.member_id = m.id
                WHERE m.id = ?
                LIMIT 1
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

    public List<MembreEntity> findByIds(List<String> ids) throws SQLException {
        if (ids == null || ids.isEmpty()) return List.of();

        String placeholders = ids.stream()
                .map(i -> "?")
                .reduce((a, b) -> a + ", " + b)
                .orElse("?");

        String sql = String.format("""
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email,
                       m.membership_date,
                       mc.occupation, mc.collectivity_id
                FROM members m
                LEFT JOIN member_collectivities mc ON mc.member_id = m.id
                WHERE m.id IN (%s)
                """, placeholders);

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < ids.size(); i++) {
                ps.setString(i + 1, ids.get(i));
            }

            List<MembreEntity> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }

    public Optional<MembreEntity> findByEmail(String email) throws SQLException {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email,
                       m.membership_date,
                       mc.occupation, mc.collectivity_id
                FROM members m
                LEFT JOIN member_collectivities mc ON mc.member_id = m.id
                WHERE m.email = ?
                LIMIT 1
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    public List<MembreEntity> findByCollectivityId(String collectivityId) throws SQLException {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email,
                       m.membership_date,
                       mc.occupation, mc.collectivity_id
                FROM members m
                INNER JOIN member_collectivities mc ON mc.member_id = m.id
                WHERE mc.collectivity_id = ?
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);

            List<MembreEntity> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }

    public List<MembreEntity> findByCollectivityAndOccupation(String collectivityId,
                                                               String occupation) throws SQLException {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email,
                       m.membership_date,
                       mc.occupation, mc.collectivity_id
                FROM members m
                INNER JOIN member_collectivities mc ON mc.member_id = m.id
                WHERE mc.collectivity_id = ? AND mc.occupation = ?::occupation_type
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setString(2, occupation);

            List<MembreEntity> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }

    public boolean existsById(String id) throws SQLException {
        String sql = "SELECT 1 FROM members WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private MembreEntity mapRow(ResultSet rs) throws SQLException {
        String occupationStr = rs.getString("occupation");
        String collectivityId = rs.getString("collectivity_id");

        return MembreEntity.builder()
                .id(rs.getString("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .birthDate(rs.getDate("birth_date").toLocalDate())
                .gender(GenderEntity.valueOf(rs.getString("gender")))
                .address(rs.getString("address"))
                .profession(rs.getString("profession"))
                .phoneNumber(String.valueOf(rs.getLong("phone_number")))
                .email(rs.getString("email"))
                .occupation(occupationStr != null
                        ? MemberOccupationEntity.valueOf(occupationStr) : null)
                .membershipDate(rs.getDate("membership_date").toLocalDate())
                .build();
    }
}
