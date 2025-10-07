package com.cronossuite.cadastros.repository.factory;

 
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "com.cronossuite.cadastros.repository.jpa.pessoa",
        "com.cronossuite.cadastros.repository.rlcl.pessoa.jpa",
        "com.cronossuite.cadastros.repository.rlcl.util.jpa",
        "com.cronossuite.cadastros.repository.crcr.**.jpa",
        "com.cronossuite.cadastros.repository.crem.**.jpa"
    },
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class OracleJpaConfig {

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
            properties.put("hibernate.enable_lazy_load_no_trans", "true");
            properties.put("hibernate.show_sql", true);
            properties.put("hibernate.format_sql", true);
            properties.put("hibernate.open-in-view", false);

        return builder
                .dataSource(dataSource)
                .packages(
                    "com.cronossuite.cadastros.repository.model.db",
                    "com.cronossuite.cadastros.repository.model.db.rlcl",
                    "com.cronossuite.cadastros.repository.model.db.crcr",
                    "com.cronossuite.cadastros.repository.model.db.crem"
                )
                .persistenceUnit("oracle")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
