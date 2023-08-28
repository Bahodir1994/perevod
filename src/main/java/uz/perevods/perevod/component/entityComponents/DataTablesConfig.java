package uz.perevods.perevod.component.entityComponents;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableJpaRepositories(basePackages = "uz.perevods.perevod.repository",
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class DataTablesConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public DataTablesRepositoryFactory dataTablesRepositoryFactory(EntityManager entityManager) {
        return new DataTablesRepositoryFactory(entityManager);
    }
}