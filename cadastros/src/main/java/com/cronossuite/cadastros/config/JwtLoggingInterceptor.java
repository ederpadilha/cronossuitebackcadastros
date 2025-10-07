package com.cronossuite.cadastros.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        
        logger.info("Request: {} {} - Authorization Header: {}", 
                   method, requestUri, 
                   authHeader != null ? "Present" : "Missing");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.debug("JWT Token (first 20 chars): {}...", 
                        token.length() > 20 ? token.substring(0, 20) : token);
        }
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        if (ex != null) {
            logger.error("Request completed with error: {}", ex.getMessage());
        } else {
            logger.info("Request completed successfully with status: {}", response.getStatus());
        }
    }
}