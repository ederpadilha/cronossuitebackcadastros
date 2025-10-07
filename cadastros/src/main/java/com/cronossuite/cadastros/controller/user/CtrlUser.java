package com.cronossuite.cadastros.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.cronossuite.cadastros.service.JwtService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/api/v1/cadastros/pessoa")
@SecurityRequirement(name = "Bearer Authentication")
public class CtrlUser {
    
    @Autowired
    JwtService jwtService;

     @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        try {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", jwtService.getCurrentUserId());
            userInfo.put("username", jwtService.getUsername());
            userInfo.put("email", jwtService.getEmail());
            userInfo.put("roles", jwtService.getRoles());
            userInfo.put("isTokenValid", jwtService.isTokenValid());
            
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put("error", "Erro ao obter informações do usuário: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorInfo);
        }
    }
}
