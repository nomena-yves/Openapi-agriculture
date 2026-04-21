package tsutsu.examfinal.Repository;

import org.springframework.stereotype.Repository;
import tsutsu.examfinal.Entity.CollectivityEntity;
import tsutsu.examfinal.config.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class CollectivityRepository {
private  final DataSource dataSource;

    public CollectivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addCOLLECtivity()throws SQLException {

            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO (name) VALUES (?)"
            );

            conn.close();
    }

}
