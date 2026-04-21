package tsutsu.examfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:4532/agricultural_collectivity");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1234");
        return dataSource;
    }
}
