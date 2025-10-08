package com.cronossuite.cadastros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.utilis.U_UserContext;

/**
 * Serviço para verificações de autorização baseadas no usuário logado
 * Substitui funcionalidades como userEMpr da classe antiga
 */
@Service
public class UserAuthorizationService {

    @Autowired
    private U_UserContext userContext;

    /**
     * Verifica se o usuário logado tem permissão para acessar uma empresa específica
     * Equivalente ao antigo userEMpr
     */
    public boolean userCanAccessCompany(Long companyId) {
        if (!userContext.isAuthenticated()) {
            return false;
        }

        // Aqui você implementaria a lógica específica do seu negócio
        // Por exemplo, verificar se o usuário tem acesso à empresa baseado em:
        // - Roles do usuário
        // - Tabela de relacionamento usuário-empresa
        // - Regras de negócio específicas

        String userId = userContext.getUserId();

        // Exemplo de lógica: 
        // - Admins têm acesso a todas as empresas
        if (userContext.hasRole("ADMIN")) {
            return true;
        }

        // - Usuários RLCL têm acesso a empresas do schema RLCL
        if (userContext.hasRole("RLCL")) {
            // Aqui você faria uma consulta ao banco para verificar
            // se o usuário tem acesso à empresa específica
            return checkUserCompanyAccess(userId, companyId);
        }

        return false;
    }

    /**
     * Verifica se o usuário pode acessar um determinado schema
     */
    public boolean userCanAccessSchema(String schema) {
        if (!userContext.isAuthenticated()) {
            return false;
        }

        var roles = userContext.getUserRoles();
        
        // Mapeia roles para schemas permitidos
        switch (schema.toLowerCase()) {
            case "rlcl":
                return roles.contains("RLCL") || roles.contains("ADMIN");
            case "crcr":
                return roles.contains("CRCR") || roles.contains("ADMIN");
            case "crem":
                return roles.contains("CREM") || roles.contains("ADMIN");
            default:
                return roles.contains("ADMIN");
        }
    }

    /**
     * Obtém o schema padrão baseado nas roles do usuário
     */
    public String getDefaultSchemaForUser() {
        if (!userContext.isAuthenticated()) {
            return "rlcl"; // default
        }

        var roles = userContext.getUserRoles();
        
        // Prioridade de schemas baseada nas roles
        if (roles.contains("RLCL")) return "rlcl";
        if (roles.contains("CRCR")) return "crcr";
        if (roles.contains("CREM")) return "crem";
        
        return "rlcl"; // default
    }

    /**
     * Verifica se o usuário tem uma permissão específica
     */
    public boolean hasPermission(String permission) {
        if (!userContext.isAuthenticated()) {
            return false;
        }

        // Aqui você implementaria a lógica de permissões específicas
        // baseada no seu sistema de autorização
        
        var roles = userContext.getUserRoles();
        
        switch (permission.toLowerCase()) {
            case "create_empresa":
                return roles.contains("ADMIN") || roles.contains("RLCL");
            case "delete_empresa":
                return roles.contains("ADMIN");
            case "view_empresa":
                return roles.contains("ADMIN") || roles.contains("RLCL") || roles.contains("CREM");
            default:
                return false;
        }
    }

    /**
     * Método privado para verificar acesso específico usuário-empresa
     * Aqui você implementaria a consulta ao banco de dados
     */
    private boolean checkUserCompanyAccess(String userId, Long companyId) {
        // TODO: Implementar consulta ao banco
        // Exemplo:
        // return userCompanyRepository.existsByUserIdAndCompanyId(userId, companyId);
        
        // Por enquanto, retorna true para não bloquear funcionalidades
        return true;
    }

    /**
     * Obtém informações resumidas do usuário para logs/auditoria
     */
    public String getUserInfoForAudit() {
        if (!userContext.isAuthenticated()) {
            return "ANONYMOUS";
        }
        
        return String.format("User[id=%s, username=%s, roles=%s]", 
            userContext.getUserId(), 
            userContext.getUsername(), 
            userContext.getUserRoles());
    }
}