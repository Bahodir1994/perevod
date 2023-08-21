package uz.perevods.perevod.configurations.asycConfig;//package uz.customs.appmonitoring.configuration.asycConfig;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    public DataSource dataSource(ObjectProvider<DataSourceProperties> dataSourcePropertiesProvider) {
//        DataSourceProperties dataSourceProperties = dataSourcePropertiesProvider.getObject();
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl("jdbc:as400:" + dataSourceProperties.getUrl());
//        hikariConfig.setUsername(dataSourceProperties.getUsername());
//        hikariConfig.setPassword(dataSourceProperties.getPassword());
//        // Configure other HikariCP properties as needed
//        return new HikariDataSource(hikariConfig);
//    }
//}
