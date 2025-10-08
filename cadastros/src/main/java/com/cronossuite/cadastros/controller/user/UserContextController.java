package com.cronossuite.cadastros.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cronossuite.cadastros.utilis.U_UserContext;
import com.cronossuite.cadastros.utilis.U_UserContext.UserInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Context", description = "Informações do usuário logado")
@SecurityRequirement(name = "Bearer Authentication")
public class UserContextController {
    
    @Autowired
    private U_UserContext userContext;

    @GetMapping("/current")
    @Operation(summary = "Usuário atual", description = "Retorna informações do usuário logado")
    public ResponseEntity<UserInfo> getCurrentUser() {
        if (userContext.isAuthenticated()) {
            return ResponseEntity.ok(userContext.getUserInfo());
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/check-auth")
    @Operation(summary = "Verificar autenticação", description = "Verifica se o usuário está autenticado")
    public ResponseEntity<Map<String, Object>> checkAuthentication() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("authenticated", userContext.isAuthenticated());
        response.put("userId", userContext.getUserId());
        response.put("username", userContext.getUsername());
        response.put("roles", userContext.getUserRoles());
        response.put("tokenValid", userContext.isTokenValid());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/roles")
    @Operation(summary = "Roles do usuário", description = "Lista as roles/permissões do usuário")
    public ResponseEntity<Map<String, Object>> getUserRoles() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("roles", userContext.getUserRoles());
        response.put("hasRlclRole", userContext.hasRole("RLCL"));
        response.put("hasAdminRole", userContext.hasRole("ADMIN"));
        
        return ResponseEntity.ok(response);
    }
}