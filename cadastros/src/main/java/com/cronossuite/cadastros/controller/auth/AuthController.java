package com.cronossuite.cadastros.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cronossuite.cadastros.service.JwtService;
import com.cronossuite.cadastros.service.TokenRefreshService;
import com.cronossuite.cadastros.service.TokenRefreshService.TokenResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para gerenciamento de autenticação e tokens")
public class AuthController {

    @Autowired
    private TokenRefreshService tokenRefreshService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica o usuário e retorna tokens")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = tokenRefreshService.login(
            request.getUsername(), request.getPassword());
        
        Map<String, Object> response = new HashMap<>();
        
        if (tokenResponse.isSuccess()) {
            response.put("success", true);
            response.put("access_token", tokenResponse.getAccessToken());
            response.put("refresh_token", tokenResponse.getRefreshToken());
            response.put("expires_in", tokenResponse.getExpiresIn());
            response.put("token_type", "Bearer");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("error", tokenResponse.getError());
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Renova o access token usando refresh token")
    @ApiResponse(responseCode = "200", description = "Token renovado com sucesso")
    @ApiResponse(responseCode = "401", description = "Refresh token inválido")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody RefreshRequest request) {
        TokenResponse tokenResponse = tokenRefreshService.refreshToken(request.getRefreshToken());
        
        Map<String, Object> response = new HashMap<>();
        
        if (tokenResponse.isSuccess()) {
            response.put("success", true);
            response.put("access_token", tokenResponse.getAccessToken());
            response.put("refresh_token", tokenResponse.getRefreshToken());
            response.put("expires_in", tokenResponse.getExpiresIn());
            response.put("token_type", "Bearer");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("error", tokenResponse.getError());
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/token-info")
    @Operation(summary = "Informações do token atual", 
               description = "Retorna informações detalhadas do token JWT atual")
    @ApiResponse(responseCode = "200", description = "Informações do token")
    public ResponseEntity<Map<String, Object>> getTokenInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Map<String, Object> tokenInfo = tokenRefreshService.getTokenInfo(token);
            
            // Adiciona informações do JwtService também
            tokenInfo.put("currentUserId", jwtService.getCurrentUserId());
            tokenInfo.put("currentUsername", jwtService.getUsername());
            tokenInfo.put("currentRoles", jwtService.getRoles());
            tokenInfo.put("isTokenStillValid", jwtService.isTokenValid());
            tokenInfo.put("isNearExpiry", tokenRefreshService.isTokenNearExpiry(token));
            
            return ResponseEntity.ok(tokenInfo);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Token não fornecido");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar token atual", 
               description = "Verifica se o token atual é válido e se precisa ser renovado")
    @ApiResponse(responseCode = "200", description = "Status de validação do token")
    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Map<String, Object> response = new HashMap<>();
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            response.put("hasToken", true);
            response.put("isValid", jwtService.isTokenValid());
            response.put("isNearExpiry", tokenRefreshService.isTokenNearExpiry(token));
            response.put("needsRefresh", tokenRefreshService.isTokenNearExpiry(token));
            response.put("userId", jwtService.getCurrentUserId());
            response.put("username", jwtService.getUsername());
            
            return ResponseEntity.ok(response);
        } else {
            response.put("hasToken", false);
            response.put("isValid", false);
            response.put("error", "Token não fornecido");
            return ResponseEntity.status(401).body(response);
        }
    }

    // Classes para requisições
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RefreshRequest {
        private String refreshToken;

        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    }
}