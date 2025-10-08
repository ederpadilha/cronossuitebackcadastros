package com.cronossuite.cadastros.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cronossuite.cadastros.service.JwtService;
import com.cronossuite.cadastros.utilis.U_UserContext;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/api/v1/cadastros/user")
@SecurityRequirement(name = "Bearer Authentication")
public class CtrlUser {
    
    @Autowired
    JwtService jwtService;
    
    @Autowired
    U_UserContext userContext;

     @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        try {
            // Usando a nova classe utilitária
            if (userContext.isAuthenticated()) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("userId", userContext.getUserId());
                userInfo.put("username", userContext.getUsername());
                userInfo.put("email", userContext.getUserEmail());
                userInfo.put("roles", userContext.getUserRoles());
                userInfo.put("isTokenValid", userContext.isTokenValid());
                userInfo.put("token", userContext.getBearerToken());
                
                return ResponseEntity.ok(userInfo);
            } else {
                Map<String, Object> errorInfo = new HashMap<>();
                errorInfo.put("error", "Usuário não autenticado");
                return ResponseEntity.status(401).body(errorInfo);
            }
        } catch (Exception e) {
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put("error", "Erro ao obter informações do usuário: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorInfo);
        }
    }
    
    @GetMapping("/context")
    public ResponseEntity<U_UserContext.UserInfo> getUserContext() {
        try {
            if (userContext.isAuthenticated()) {
                return ResponseEntity.ok(userContext.getUserInfo());
            } else {
                return ResponseEntity.status(401).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
