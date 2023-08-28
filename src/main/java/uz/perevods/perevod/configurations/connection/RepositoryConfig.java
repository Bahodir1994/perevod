//package uz.perevods.perevod.configurations.connection;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Configuration
//@ConfigurationProperties("spring.datasource.oracle")
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactoryOracle",
//        transactionManagerRef = "transactionManagerOracle"
//)
//public class DataTablesConfig {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Bean
//    public DataTablesRepositoryFactoryBean<?> dataTableRepositoryFactoryBean() {
//        return new DataTablesRepositoryFactoryBean<>(DataTablesRepository.class);
//    }
//}
