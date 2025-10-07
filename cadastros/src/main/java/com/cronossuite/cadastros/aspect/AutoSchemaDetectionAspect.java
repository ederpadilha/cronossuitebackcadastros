package com.cronossuite.cadastros.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.service.SchemaIdentificationService;

/**
 * Interceptor automático para identificação de schema baseado na entidade
 */
@Aspect
@Component
public class AutoSchemaDetectionAspect {
    
    @Autowired
    private SchemaIdentificationService schemaIdentificationService;
    
    /**
     * Intercepta métodos de repositórios JPA para definir schema automaticamente
     */
    @Around("execution(* com.cronossuite.cadastros.repository.jpa.*.*(..))")
    public Object aroundRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String currentSchema = schemaIdentificationService.getCurrentSchema();
        
        try {
            // Se não há schema definido, define baseado no contexto
            if ("rlcl".equals(currentSchema)) {
                // Extrai o nome da entidade do nome da classe do repositório
                String repositoryName = joinPoint.getTarget().getClass().getSimpleName();
                String entityName = extractEntityNameFromRepository(repositoryName);
                
                if (entityName != null) {
                    schemaIdentificationService.setSchemaByObject(entityName);
                }
            }
            
            return joinPoint.proceed();
        } finally {
            // Mantém o schema se foi definido externamente
            if ("rlcl".equals(currentSchema)) {
                schemaIdentificationService.clearSchema();
            }
        }
    }
    
    /**
     * Extrai o nome da entidade a partir do nome do repositório
     * Ex: JpaTblPessoas -> TblPessoas
     */
    private String extractEntityNameFromRepository(String repositoryName) {
        if (repositoryName.startsWith("Jpa") && repositoryName.length() > 3) {
            return repositoryName.substring(3);
        }
        return null;
    }
}