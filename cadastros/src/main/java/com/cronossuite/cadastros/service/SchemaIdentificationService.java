package com.cronossuite.cadastros.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.repository.factory.DataSourceContextHolder;

/**
 * Serviço para identificar e gerenciar schemas baseado em contexto
 */
@Service
public class SchemaIdentificationService {
    
    @Autowired
    private JwtService jwtService;
    
    // Mapeamento de usuários para schemas
    private static final Map<String, String> USER_SCHEMA_MAP = new HashMap<>();
    
    // Mapeamento de roles para schemas
    private static final Map<String, String> ROLE_SCHEMA_MAP = new HashMap<>();
    
    // Mapeamento de objetos para schemas
    private static final Map<String, String> OBJECT_SCHEMA_MAP = new HashMap<>();
    
    static {
        // Configuração de usuários -> schemas
        USER_SCHEMA_MAP.put("admin", "rlcl");
        USER_SCHEMA_MAP.put("cadastros", "rlcl");
        USER_SCHEMA_MAP.put("cronos", "crcr");
        USER_SCHEMA_MAP.put("clientes", "crem");
        
        // Configuração de roles -> schemas
        ROLE_SCHEMA_MAP.put("ADMIN", "rlcl");
        ROLE_SCHEMA_MAP.put("RLCL", "rlcl");
        ROLE_SCHEMA_MAP.put("CRCR", "crcr");
        ROLE_SCHEMA_MAP.put("CREM", "crem");
        
        // Configuração de objetos -> schemas
        OBJECT_SCHEMA_MAP.put("TblPessoas", "rlcl");
        OBJECT_SCHEMA_MAP.put("TblCabecaLookups", "rlcl");
        OBJECT_SCHEMA_MAP.put("TblLinhaLookups", "rlcl");
        OBJECT_SCHEMA_MAP.put("VwLookpusLinhas", "rlcl");
        
    //    OBJECT_SCHEMA_MAP.put("TblCronosConfig", "crcr");
    //    OBJECT_SCHEMA_MAP.put("TblCronosUsuarios", "crcr");
    //    OBJECT_SCHEMA_MAP.put("TblCronosModulos", "crcr");
   //     OBJECT_SCHEMA_MAP.put("TblCronosParametros", "crcr");
        
        OBJECT_SCHEMA_MAP.put("TblPropriedade", "crem");
   //     OBJECT_SCHEMA_MAP.put("TblClientesContatos", "crem");
   //     OBJECT_SCHEMA_MAP.put("TblClientesEnderecos", "crem");
   //     OBJECT_SCHEMA_MAP.put("TblClientesHistorico", "crem");
    }
    
    /**
     * Identifica o schema baseado no usuário logado
     */
    public String getSchemaByCurrentUser() {
        String username = jwtService.getUsername();
        if (username != null) {
            return USER_SCHEMA_MAP.getOrDefault(username.toLowerCase(), "rlcl");
        }
        return "rlcl"; // default
    }
    
    /**
     * Identifica o schema baseado na role do usuário
     */
    public String getSchemaByUserRole() {
        var roles = jwtService.getRoles();
        if (roles != null && !roles.isEmpty()) {
            for (String role : roles) {
                String schema = ROLE_SCHEMA_MAP.get(role.toUpperCase());
                if (schema != null) {
                    return schema;
                }
            }
        }
        return "rlcl"; // default
    }
    
    /**
     * Identifica o schema baseado no objeto/entidade
     */
    public String getSchemaByObject(String objectName) {
        return OBJECT_SCHEMA_MAP.getOrDefault(objectName, "rlcl");
    }
    
    /**
     * Identifica o schema baseado na classe da entidade
     */
    public String getSchemaByEntity(Class<?> entityClass) {
        return getSchemaByObject(entityClass.getSimpleName());
    }
    
    /**
     * Define automaticamente o schema baseado no contexto atual
     */
    public void setSchemaByContext() {
        String schema = determineSchemaByContext();
        DataSourceContextHolder.setDataSourceType(schema);
    }
    
    /**
     * Define o schema baseado no objeto que será usado
     */
    public void setSchemaByObject(String objectName) {
        String schema = getSchemaByObject(objectName);
        DataSourceContextHolder.setDataSourceType(schema);
    }
    
    /**
     * Define o schema baseado na entidade
     */
    public void setSchemaByEntity(Class<?> entityClass) {
        String schema = getSchemaByEntity(entityClass);
        DataSourceContextHolder.setDataSourceType(schema);
    }
    
    /**
     * Obtém o schema atual definido no contexto
     */
    public String getCurrentSchema() {
        String schema = DataSourceContextHolder.getDataSourceType();
        return schema != null ? schema : "rlcl";
    }
    
    /**
     * Determina o schema baseado em múltiplos fatores
     */
    private String determineSchemaByContext() {
        // Prioridade 1: Schema já definido no contexto
        String currentSchema = DataSourceContextHolder.getDataSourceType();
        if (currentSchema != null) {
            return currentSchema;
        }
        
        // Prioridade 2: Baseado na role do usuário
        String schemaByRole = getSchemaByUserRole();
        if (!"rlcl".equals(schemaByRole)) {
            return schemaByRole;
        }
        
        // Prioridade 3: Baseado no usuário
        return getSchemaByCurrentUser();
    }
    
    /**
     * Limpa o contexto do schema
     */
    public void clearSchema() {
        DataSourceContextHolder.clearDataSourceType();
    }
    
    /**
     * Retorna informações do contexto atual
     */
    public Map<String, Object> getContextInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("currentSchema", getCurrentSchema());
        info.put("username", jwtService.getUsername());
        info.put("roles", jwtService.getRoles());
        info.put("schemaByUser", getSchemaByCurrentUser());
        info.put("schemaByRole", getSchemaByUserRole());
        return info;
    }
    
    /**
     * Lista todos os schemas disponíveis
     */
    public Map<String, String> getAvailableSchemas() {
        Map<String, String> schemas = new HashMap<>();
        schemas.put("rlcl", "RLCL - Cadastros Gerais");
        schemas.put("crcr", "CRCR - Cadastros Cronos");
        schemas.put("crem", "CREM - Cadastros Clientes");
        return schemas;
    }
}