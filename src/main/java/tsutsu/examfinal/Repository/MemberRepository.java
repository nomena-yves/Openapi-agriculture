package tsutsu.examfinal.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.examfinal.Entity.GenderEntity;
import tsutsu.examfinal.Entity.MemberOccupationEntity;
import tsutsu.examfinal.Entity.MembreEntity;
import tsutsu.examfinal.config.DatabaseConfig;

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
                    (first_name, last_name, birth_date, gender, address,
                     profession, phone_number, email, occupation,
                     membership_date, collectivity_id)
                VALUES (?, ?, ?, ?::gender_type, ?, ?, ?, ?, ?::occupation_type, ?, ?::uuid)
                RETURNING id
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, member.getFirstName());
            ps.setString(2, member.getLastName());
            ps.setDate(3, Date.valueOf(member.getBirthDate()));
            ps.setString(4, member.getGender().name());
            ps.setString(5, member.getAdress());
            ps.setString(6, member.getProfession());
            ps.setLong(7, Long.parseLong(member.getPhoneNumber()));
            ps.setString(8, member.getEmail());
            ps.setString(9, member.getOccupation().name());
            ps.setDate(10, Date.valueOf(member.getMembershipDate()));
            ps.setString(11, String.valueOf(member.getCollectivity()));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getString("id");
            }
        }
    }

    public void saveReferees(String memberId, List<String> refereeIds) throws SQLException {
        String sql = "INSERT INTO member_referees (member_id, referee_id) VALUES (?::uuid, ?::uuid)";

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
                SELECT id, first_name, last_name, birth_date, gender, address,
                       profession, phone_number, email, occupation,
                       membership_date, collectivity_id
                FROM members
                WHERE id = ?::uuid
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
                .map(i -> "?::uuid")
                .reduce((a, b) -> a + ", " + b)
                .orElse("?::uuid");

        String sql = String.format("""
                SELECT id, first_name, last_name, birth_date, gender, address,
                       profession, phone_number, email, occupation,
                       membership_date, collectivity_id
                FROM members
                WHERE id IN (%s)
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
                SELECT id, first_name, last_name, birth_date, gender, address,
                       profession, phone_number, email, occupation,
                       membership_date, collectivity_id
                FROM members
                WHERE email = ?
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

    public List<MembreEntity> findRefereesByMemberId(String memberId) throws SQLException {
        String sql = """
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email,
                       m.occupation, m.membership_date, m.collectivity_id
                FROM members m
                INNER JOIN member_referees mr ON mr.referee_id = m.id
                WHERE mr.member_id = ?::uuid
                """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, memberId);

            List<MembreEntity> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            return result;
        }
    }

    public List<MembreEntity> findByCollectivityId(String collectivityId) throws SQLException {
        String sql = """
                SELECT id, first_name, last_name, birth_date, gender, address,
                       profession, phone_number, email, occupation,
                       membership_date, collectivity_id
                FROM members
                WHERE collectivity_id = ?::uuid
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

    public boolean existsById(String id) throws SQLException {
        String sql = "SELECT 1 FROM members WHERE id = ?::uuid";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private MembreEntity mapRow(ResultSet rs) throws SQLException {
        return MembreEntity.builder()
                .id(rs.getString("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .birthDate(rs.getDate("birth_date").toLocalDate())
                .gender(GenderEntity.valueOf(rs.getString("gender")))
                .adress(rs.getString("address"))
                .profession(rs.getString("profession"))
                .phoneNumber(String.valueOf(rs.getLong("phone_number")))
                .email(rs.getString("email"))
                .Occupation(MemberOccupationEntity.valueOf(rs.getString("occupation")))
                .membershipDate(rs.getDate("membership_date").toLocalDate())
                .collectivity(rs.getString("collectivity_id"))
                .build();
    }
}
