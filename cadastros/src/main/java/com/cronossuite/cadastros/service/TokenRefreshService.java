package com.cronossuite.cadastros.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenRefreshService {

    @Value("${jwt.auth-server:http://localhost:9092}")
    private String authServerUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public TokenRefreshService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Verifica se o token está próximo do vencimento (dentro de 5 minutos)
     */
    public boolean isTokenNearExpiry(String token) {
        try {
            String[] chunks = token.split("\\.");
            if (chunks.length != 3) {
                return true; // Token inválido, considera como expirado
            }

            // Decodifica o payload do JWT
            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            JsonNode payloadJson = objectMapper.readTree(payload);
            
            if (payloadJson.has("exp")) {
                long exp = payloadJson.get("exp").asLong();
                Instant expiryTime = Instant.ofEpochSecond(exp);
                Instant now = Instant.now();
                Instant fiveMinutesFromNow = now.plusSeconds(300); // 5 minutos
                
                return expiryTime.isBefore(fiveMinutesFromNow);
            }
            
            return true; // Se não tem exp, considera como expirado
        } catch (Exception e) {
            return true; // Em caso de erro, considera como expirado
        }
    }

    /**
     * Obtém informações do token (exp, iat, etc.)
     */
    public Map<String, Object> getTokenInfo(String token) {
        Map<String, Object> info = new HashMap<>();
        try {
            String[] chunks = token.split("\\.");
            if (chunks.length != 3) {
                info.put("valid", false);
                info.put("error", "Token inválido");
                return info;
            }

            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            JsonNode payloadJson = objectMapper.readTree(payload);
            
            info.put("valid", true);
            
            if (payloadJson.has("exp")) {
                long exp = payloadJson.get("exp").asLong();
                LocalDateTime expiryTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(exp), ZoneId.systemDefault());
                info.put("expiresAt", expiryTime.toString());
                info.put("isExpired", Instant.ofEpochSecond(exp).isBefore(Instant.now()));
            }
            
            if (payloadJson.has("iat")) {
                long iat = payloadJson.get("iat").asLong();
                LocalDateTime issuedAt = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(iat), ZoneId.systemDefault());
                info.put("issuedAt", issuedAt.toString());
            }
            
            if (payloadJson.has("sub")) {
                info.put("subject", payloadJson.get("sub").asText());
            }
            
            if (payloadJson.has("username")) {
                info.put("username", payloadJson.get("username").asText());
            }
            
            if (payloadJson.has("preferred_username")) {
                info.put("preferredUsername", payloadJson.get("preferred_username").asText());
            }
            
        } catch (Exception e) {
            info.put("valid", false);
            info.put("error", "Erro ao processar token: " + e.getMessage());
        }
        
        return info;
    }

    /**
     * Renova o token usando refresh token
     */
    public TokenResponse refreshToken(String refreshToken) {
        try {
            String url = authServerUrl + "/auth/refresh";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> request = new HashMap<>();
            request.put("refresh_token", refreshToken);
            
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
            
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> body = response.getBody();
                return new TokenResponse(
                    true,
                    (String) body.get("access_token"),
                    (String) body.get("refresh_token"),
                    (Integer) body.get("expires_in"),
                    null
                );
            }
            
            return new TokenResponse(false, null, null, null, "Resposta inválida do servidor");
            
        } catch (HttpClientErrorException e) {
            return new TokenResponse(false, null, null, null, 
                "Erro HTTP " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return new TokenResponse(false, null, null, null, 
                "Erro ao renovar token: " + e.getMessage());
        }
    }

    /**
     * Faz login para obter novos tokens
     */
    public TokenResponse login(String username, String password) {
        try {
            String url = authServerUrl + "/auth/login";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> request = new HashMap<>();
            request.put("username", username);
            request.put("password", password);
            
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
            
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> body = response.getBody();
                return new TokenResponse(
                    true,
                    (String) body.get("access_token"),
                    (String) body.get("refresh_token"),
                    (Integer) body.get("expires_in"),
                    null
                );
            }
            
            return new TokenResponse(false, null, null, null, "Credenciais inválidas");
            
        } catch (HttpClientErrorException e) {
            return new TokenResponse(false, null, null, null, 
                "Erro HTTP " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return new TokenResponse(false, null, null, null, 
                "Erro ao fazer login: " + e.getMessage());
        }
    }

    /**
     * Classe para resposta de tokens
     */
    public static class TokenResponse {
        private final boolean success;
        private final String accessToken;
        private final String refreshToken;
        private final Integer expiresIn;
        private final String error;

        public TokenResponse(boolean success, String accessToken, String refreshToken, 
                           Integer expiresIn, String error) {
            this.success = success;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresIn = expiresIn;
            this.error = error;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getAccessToken() { return accessToken; }
        public String getRefreshToken() { return refreshToken; }
        public Integer getExpiresIn() { return expiresIn; }
        public String getError() { return error; }
    }
}