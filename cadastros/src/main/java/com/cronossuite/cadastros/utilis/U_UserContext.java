package com.cronossuite.cadastros.utilis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cronossuite.cadastros.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utilitário para captura de informações do usuário logado
 * Substitui a antiga U_tokenStist adaptando para o novo sistema JWT
 */
@Component
public class U_UserContext {

    @Autowired
    private JwtService jwtService;

    /**
     * Obtém o header Authorization da requisição atual
     */
    public String getAuthorizationHeader() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? request.getHeader("Authorization") : null;
    }

    /**
     * Obtém o token JWT puro (sem o prefixo "Bearer ")
     */
    public String getBearerToken() {
        String authHeader = getAuthorizationHeader();
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * Obtém o ID do usuário logado (subject do JWT)
     */
    public String getUserId() {
        return jwtService.getCurrentUserId();
    }

    /**
     * Obtém o username do usuário logado
     */
    public String getUsername() {
        return jwtService.getUsername();
    }

    /**
     * Obtém o email do usuário logado
     */
    public String getUserEmail() {
        return jwtService.getEmail();
    }

    /**
     * Obtém as roles/authorities do usuário logado
     */
    public List<String> getUserRoles() {
        return jwtService.getRoles();
    }

    /**
     * Verifica se o usuário tem uma role específica
     */
    public boolean hasRole(String role) {
        return jwtService.hasRole(role);
    }

    /**
     * Verifica se o token ainda é válido
     */
    public boolean isTokenValid() {
        return jwtService.isTokenValid();
    }

    /**
     * Obtém informações completas do usuário em um objeto
     */
    public UserInfo getUserInfo() {
        return new UserInfo(
            getUserId(),
            getUsername(),
            getUserEmail(),
            getUserRoles(),
            getBearerToken(),
            isTokenValid()
        );
    }

    /**
     * Verifica se o usuário está autenticado
     */
    public boolean isAuthenticated() {
        return getUserId() != null && isTokenValid();
    }

    /**
     * Obtém a requisição HTTP atual
     */
    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Classe interna para representar informações do usuário
     */
    public static class UserInfo {
        private final String userId;
        private final String username;
        private final String email;
        private final List<String> roles;
        private final String token;
        private final boolean isValid;

        public UserInfo(String userId, String username, String email, 
                       List<String> roles, String token, boolean isValid) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.roles = roles;
            this.token = token;
            this.isValid = isValid;
        }

        // Getters
        public String getUserId() { return userId; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public List<String> getRoles() { return roles; }
        public String getToken() { return token; }
        public boolean isValid() { return isValid; }

        @Override
        public String toString() {
            return String.format("UserInfo{userId='%s', username='%s', email='%s', roles=%s, isValid=%s}", 
                               userId, username, email, roles, isValid);
        }
    }
}