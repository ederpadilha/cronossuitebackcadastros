package com.cronossuite.cadastros.repository.factory;

import java.beans.PropertyVetoException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cronossuite.cadastros.repository.model.cfg.Cgf;
import com.cronossuite.cadastros.repository.model.cfg.DbOracle;
import com.cronossuite.cadastros.utilis.U_ConfigReader;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class JpaFactory {

    @Autowired
    private U_ConfigReader configReader;
    
    // DESABILITADO - Usando DynamicDataSource agora
    // @Bean(name="dataSource")
    // @Primary
    public ComboPooledDataSource dataSourceLegacy() throws PropertyVetoException {
        Cgf config = configReader.getValue();
        
        if (config == null || config.getDbOracle() == null) {
            throw new IllegalStateException(
                "Configuração de banco não encontrada. " +
                "Verifique se o arquivo JSON existe e está configurado corretamente no caminho: " +
                "rlcl.config.file.path"
            );
        }
        
        DbOracle dbConfig = config.getDbOracle();
        
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("oracle.jdbc.OracleDriver");
        dataSource.setJdbcUrl(dbConfig.getConnectString());
        dataSource.setUser(dbConfig.getUser());
        dataSource.setPassword(dbConfig.getPassword());
        
        return dataSource;
    }
}
