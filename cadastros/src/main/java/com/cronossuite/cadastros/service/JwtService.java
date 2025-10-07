package com.cronossuite.cadastros.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    /**
     * Obtém o JWT do contexto de segurança atual
     */
    public Jwt getCurrentJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Obtém o subject (usuário) do token
     */
    public String getCurrentUserId() {
        Jwt jwt = getCurrentJwt();
        return jwt != null ? jwt.getSubject() : null;
    }

    /**
     * Obtém uma claim específica do token
     */

    public <T> T getClaim(String claimName, Class<T> clazz) {
        Jwt jwt = getCurrentJwt();
        if (jwt != null) {
            return jwt.getClaim(claimName);
        }
        return null;
    }

    /**
     * Obtém o username do token (pode estar em 'preferred_username', 'username' ou 'sub')
     */
    public String getUsername() {
        Jwt jwt = getCurrentJwt();
        if (jwt != null) {
            // Tenta diferentes claims comuns para username
            String username = jwt.getClaim("preferred_username");
            if (username == null) {
                username = jwt.getClaim("username");
            }
            if (username == null) {
                username = jwt.getClaim("name");
            }
            if (username == null) {
                username = jwt.getSubject();
            }
            return username;
        }
        return null;
    }

    /**
     * Obtém as roles/authorities do token
     */
    @SuppressWarnings("unchecked")
    public List<String> getRoles() {
        Jwt jwt = getCurrentJwt();
        if (jwt != null) {
            // Tenta diferentes claims comuns para roles
            List<String> roles = jwt.getClaim("roles");
            if (roles == null) {
                roles = jwt.getClaim("authorities");
            }
            if (roles == null) {
                // Verifica se existe realm_access.roles (comum em Keycloak)
                Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                if (realmAccess != null) {
                    roles = (List<String>) realmAccess.get("roles");
                }
            }
            return roles;
        }
        return List.of();
    }

    /**
     * Verifica se o usuário tem uma role específica
     */
    public boolean hasRole(String role) {
        List<String> roles = getRoles();
        return roles != null && roles.contains(role);
    }

    /**
     * Obtém o email do token
     */
    public String getEmail() {
        return getClaim("email", String.class);
    }

    /**
     * Verifica se o token ainda é válido (não expirado)
     */
    public boolean isTokenValid() {
        Jwt jwt = getCurrentJwt();
        if (jwt != null) {
            return jwt.getExpiresAt() != null && jwt.getExpiresAt().isAfter(java.time.Instant.now());
        }
        return false;
    }
}