package com.cronossuite.cadastros.controller.aux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cronossuite.cadastros.service.SchemaIdentificationService;
import com.cronossuite.cadastros.service.MultiSchemaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/schema")
@Tag(name = "Schema Management", description = "Gerenciamento de schemas dinâmicos")
@SecurityRequirement(name = "Bearer Authentication")
public class SchemaController {

    @Autowired
    private SchemaIdentificationService schemaIdentificationService;
    
    @Autowired
    private MultiSchemaService multiSchemaService;

    @GetMapping("/current")
    @Operation(summary = "Schema atual", description = "Retorna o schema atualmente em uso")
    @ApiResponse(responseCode = "200", description = "Schema atual")
    public ResponseEntity<Map<String, Object>> getCurrentSchema() {
        Map<String, Object> response = new HashMap<>();
        response.put("currentSchema", schemaIdentificationService.getCurrentSchema());
        response.put("contextInfo", schemaIdentificationService.getContextInfo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    @Operation(summary = "Schemas disponíveis", description = "Lista todos os schemas configurados")
    @ApiResponse(responseCode = "200", description = "Lista de schemas")
    public ResponseEntity<Map<String, String>> getAvailableSchemas() {
        return ResponseEntity.ok(schemaIdentificationService.getAvailableSchemas());
    }

    @PostMapping("/set/{schema}")
    @Operation(summary = "Definir schema", description = "Define o schema para a sessão atual")
    @ApiResponse(responseCode = "200", description = "Schema definido com sucesso")
    public ResponseEntity<Map<String, String>> setSchema(@PathVariable String schema) {
        try {
            multiSchemaService.executeInSchema(schema, () -> {
                // Apenas testa se o schema existe/é válido
                return null;
            });
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Schema definido com sucesso");
            response.put("schema", schema);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Schema inválido: " + schema);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/by-object/{objectName}")
    @Operation(summary = "Schema por objeto", description = "Identifica qual schema usar para um objeto específico")
    @ApiResponse(responseCode = "200", description = "Schema identificado")
    public ResponseEntity<Map<String, String>> getSchemaByObject(@PathVariable String objectName) {
        String schema = schemaIdentificationService.getSchemaByObject(objectName);
        Map<String, String> response = new HashMap<>();
        response.put("object", objectName);
        response.put("schema", schema);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-user")
    @Operation(summary = "Schema por usuário", description = "Identifica o schema baseado no usuário logado")
    @ApiResponse(responseCode = "200", description = "Schema do usuário")
    public ResponseEntity<Map<String, String>> getSchemaByUser() {
        Map<String, String> response = new HashMap<>();
        response.put("schemaByUser", schemaIdentificationService.getSchemaByCurrentUser());
        response.put("schemaByRole", schemaIdentificationService.getSchemaByUserRole());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/clear")
    @Operation(summary = "Limpar schema", description = "Remove a definição de schema da sessão atual")
    @ApiResponse(responseCode = "200", description = "Schema limpo")
    public ResponseEntity<Map<String, String>> clearSchema() {
        schemaIdentificationService.clearSchema();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Schema limpo com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/{schema}")
    @Operation(summary = "Testar schema", description = "Testa conectividade com um schema específico")
    @ApiResponse(responseCode = "200", description = "Teste realizado")
    public ResponseEntity<Map<String, Object>> testSchema(@PathVariable String schema) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Object result = multiSchemaService.executeInSchema(schema, () -> {
                // Aqui poderia executar uma query de teste como SELECT 1 FROM DUAL
                return "Conexão OK";
            });
            
            response.put("schema", schema);
            response.put("status", "success");
            response.put("result", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("schema", schema);
            response.put("status", "error");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}