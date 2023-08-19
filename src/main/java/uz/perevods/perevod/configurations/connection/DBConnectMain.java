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
//import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Objects;
//import java.util.Properties;
//
//@Configuration
//@ConfigurationProperties("spring.datasource.main1")
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class,
//        entityManagerFactoryRef = "entityManagerFactoryMain1",
//        transactionManagerRef = "transactionManagerMain1",
//        basePackages = {"uz.perevods.perevod.repositorys"}
//)
//public class DBConnectMain {
//
//    protected final String PERSISTENCE_UNIT_NAME = "main1";
//    protected final Properties JPA_MAIN1 = new Properties() {{
//        put("hibernate.hbm2ddl.auto", "update");
//        put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
//        put("hibernate.connection.provider_disables_autocommit", "false");
//        put("hibernate.show_sql", "true");
//        put("hibernate.format_sql", "true");
//    }};
//
//    @Bean
//    @Qualifier("main1")
//    public DataSource main1() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("org.sqlite.JDBC");
//        hikariConfig.setJdbcUrl("jdbc:sqlite:sqlitesample.db");
//        hikariConfig.setAutoCommit(true);
//
//        return new HikariDataSource(hikariConfig);
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactoryMain1")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMain1(
//            final @Qualifier("main1") DataSource main1) {
//        return new LocalContainerEntityManagerFactoryBean() {{
//            setDataSource(main1);
//            setPersistenceProviderClass(HibernatePersistenceProvider.class);
//            setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
//            setPackagesToScan("uz.perevods.perevod.entitys");
//            setJpaProperties(JPA_MAIN1);
//        }};
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager transactionManagerMain1(
//            final @Qualifier("entityManagerFactoryMain1") LocalContainerEntityManagerFactoryBean entityManagerFactoryMain1) {
//        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryMain1.getObject()));
//    }
//}
