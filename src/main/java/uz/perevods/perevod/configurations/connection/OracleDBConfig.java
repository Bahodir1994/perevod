//package uz.perevods.perevod.configurations.connection;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.hibernate.jpa.HibernatePersistenceProvider;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@ConfigurationProperties("spring.datasource.oracle")
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactoryOracle",
//        transactionManagerRef = "transactionManagerOracle",
//        basePackages = {"uz.perevods.perevod.repository"}
//)
//public class OracleDBConfig {
//    private static final Properties JPA_ORACLE = new Properties() {{
//        put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
//        put("hibernate.hbm2ddl.auto", "update");
//        put("hibernate.show_sql", "true");
//    }};
//
//    @Primary
//    @Bean(name = "dataSourceOracle")
//    public DataSource dataSourceOracle() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
//        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:databaseglob");
//        hikariConfig.setUsername("SYSTEM");
//        hikariConfig.setPassword("qwerty123ytrewq321IB");
//
//        return new HikariDataSource(hikariConfig);
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactoryOracle")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryOracle(
//            @Qualifier("dataSourceOracle") DataSource dataSourceOracle) {
//        return new LocalContainerEntityManagerFactoryBean() {{
//            setDataSource(dataSourceOracle);
//            setPackagesToScan("uz.perevods.perevod.entity");
//            setPersistenceProviderClass(HibernatePersistenceProvider.class);
//            setJpaProperties(JPA_ORACLE);
//        }};
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager transactionManagerOracle(
//            @Qualifier("entityManagerFactoryOracle") LocalContainerEntityManagerFactoryBean entityManagerFactoryOracle) {
//        return new JpaTransactionManager(entityManagerFactoryOracle.getObject());
//    }
//}
