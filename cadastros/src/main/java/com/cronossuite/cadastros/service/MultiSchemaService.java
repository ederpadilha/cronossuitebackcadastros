package com.cronossuite.cadastros.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.repository.factory.DataSourceContextHolder;

import java.util.function.Supplier;

@Service
public class MultiSchemaService {
    
    /**
     * Executa uma operação no contexto de um schema específico
     */
    public <T> T executeInSchema(String schema, Supplier<T> operation) {
        String previousSchema = DataSourceContextHolder.getDataSourceType();
        try {
            DataSourceContextHolder.setDataSourceType(schema);
            return operation.get();
        } finally {
            if (previousSchema != null) {
                DataSourceContextHolder.setDataSourceType(previousSchema);
            } else {
                DataSourceContextHolder.clearDataSourceType();
            }
        }
    }
    
    /**
     * Executa uma operação void no contexto de um schema específico
     */
    public void executeInSchema(String schema, Runnable operation) {
        String previousSchema = DataSourceContextHolder.getDataSourceType();
        try {
            DataSourceContextHolder.setDataSourceType(schema);
            operation.run();
        } finally {
            if (previousSchema != null) {
                DataSourceContextHolder.setDataSourceType(previousSchema);
            } else {
                DataSourceContextHolder.clearDataSourceType();
            }
        }
    }
    
    // Métodos de conveniência para schemas específicos
    public <T> T executeInCadastros(Supplier<T> operation) {
        return executeInSchema("cadastros", operation);
    }
    
    public <T> T executeInFinanceiro(Supplier<T> operation) {
        return executeInSchema("financeiro", operation);
    }
    
    public <T> T executeInEstoque(Supplier<T> operation) {
        return executeInSchema("estoque", operation);
    }
    
    public <T> T executeInVendas(Supplier<T> operation) {
        return executeInSchema("vendas", operation);
    }
}