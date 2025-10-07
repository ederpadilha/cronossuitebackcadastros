package com.cronossuite.cadastros.repository.factory;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.cronossuite.cadastros.repository.model.cfg.Cgf;
import com.cronossuite.cadastros.repository.model.cfg.DbOracle;
import com.cronossuite.cadastros.utilis.U_ConfigReader;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DynamicDataSourceConfig {

    @Autowired
    private U_ConfigReader configReader;
    
    @Bean
    @Primary
    public DynamicDataSource dataSource() throws PropertyVetoException {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        
        Cgf config = configReader.getValue();
        if (config == null) {
            throw new IllegalStateException("Configuração não encontrada");
        }
        
        Map<Object, Object> targetDataSources = new HashMap<>();
        
        // Adicionar DataSource principal (compatibilidade)
        if (config.getDbOracle() != null) {
            targetDataSources.put("default", createDataSourceFromConfig(config.getDbOracle()));
            targetDataSources.put("rlcl", createDataSourceFromConfig(config.getDbOracle()));
        }
        
        // Adicionar DataSources dos schemas configurados
        if (config.getSchemas() != null) {
            config.getSchemas().forEach((schemaName, dbConfig) -> {
                try {
                    targetDataSources.put(schemaName, createDataSourceFromConfig(dbConfig));
                } catch (PropertyVetoException e) {
                    throw new RuntimeException("Erro ao criar DataSource para schema: " + schemaName, e);
                }
            });
        }
         
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get("default"));
        
        return dynamicDataSource;
    }
    
    private ComboPooledDataSource createDataSourceFromConfig(DbOracle dbConfig) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("oracle.jdbc.OracleDriver");
        dataSource.setJdbcUrl(dbConfig.getConnectString());
        dataSource.setUser(dbConfig.getUser());
        dataSource.setPassword(dbConfig.getPassword());
        
        // Configurações otimizadas para Oracle
        dataSource.setInitialPoolSize(3);
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(15);
        dataSource.setMaxIdleTime(300);
        dataSource.setCheckoutTimeout(5000);
        dataSource.setMaxConnectionAge(3600); // 1 hora
        dataSource.setIdleConnectionTestPeriod(300); // 5 minutos
        dataSource.setTestConnectionOnCheckout(true);
        dataSource.setPreferredTestQuery("SELECT 1 FROM DUAL");
        
        return dataSource;
    }
}