package com.cronossuite.cadastros.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.annotation.UseSchema;
import com.cronossuite.cadastros.repository.factory.DataSourceContextHolder;

/**
 * Aspect para interceptar métodos anotados com @UseSchema
 * e configurar o DataSource apropriado
 */
@Aspect
@Component
public class SchemaSelectionAspect {
    
    @Around("@annotation(useSchema)")
    public Object switchSchema(ProceedingJoinPoint joinPoint, UseSchema useSchema) throws Throwable {
        String currentSchema = DataSourceContextHolder.getDataSourceType();
        String targetSchema = useSchema.value();
        
        try {
            DataSourceContextHolder.setDataSourceType(targetSchema);
            return joinPoint.proceed();
        } finally {
            if (currentSchema != null) {
                DataSourceContextHolder.setDataSourceType(currentSchema);
            } else {
                DataSourceContextHolder.clearDataSourceType();
            }
        }
    }
}