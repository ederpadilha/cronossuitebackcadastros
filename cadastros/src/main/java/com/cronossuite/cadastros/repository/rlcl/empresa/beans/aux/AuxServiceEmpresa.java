package com.cronossuite.cadastros.repository.rlcl.empresa.beans.aux;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.repository.model.db.rlcl.empresa.TblEmpresa;
import com.cronossuite.cadastros.repository.model.db.rlcl.empresa.VW_UserEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.jpa.JpaTblEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.jpa.JpaVW_UserEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.saida.SaiEmpresa;
import com.cronossuite.cadastros.service.SchemaIdentificationService;
import com.cronossuite.cadastros.utilis.U_UserContext;
import com.cronossuite.cadastros.utilis.U_bolleans;
import com.cronossuite.cadastros.utilis.U_random;

@Component
public class AuxServiceEmpresa {
    
    @Autowired
    JpaTblEmpresa jpaTblEmpresa;
    @Autowired
    JpaVW_UserEmpresa jpaVW_UserEmpresa;
    
    @Autowired
    U_UserContext userContext;
    
    @Autowired
    SchemaIdentificationService schemaIdentificationService;

    U_random r = new U_random();
    U_bolleans b = new U_bolleans();


    private SaiEmpresa converter (VW_UserEmpresa vwEmpresa){
        SaiEmpresa saiEmpresa = new SaiEmpresa();
        saiEmpresa.setId(vwEmpresa.getCodigo());
        saiEmpresa.setIdentificacao(vwEmpresa.getIdentificador());
        saiEmpresa.setNome(vwEmpresa.getNome());
        saiEmpresa.setAtivo(b.voltaDados(vwEmpresa.getAtivo()));
        return saiEmpresa;
    }

    private SaiEmpresa converter (TblEmpresa tblEmpresa){
        SaiEmpresa saiEmpresa = new SaiEmpresa();
        saiEmpresa.setId(tblEmpresa.getCodigo());
        saiEmpresa.setIdentificacao(tblEmpresa.getIdentificador());
        saiEmpresa.setNome(tblEmpresa.getNome());
        saiEmpresa.setAtivo(b.voltaDados(tblEmpresa.getAtivo()));
        return saiEmpresa;
    }

    private TblEmpresa converter (SaiEmpresa saiEmpresa , String Tipo , Long id , String codigo){
        TblEmpresa tblEmpresa = new TblEmpresa();
        if (Tipo.equals("post") ){
            tblEmpresa.setCodigo(r.generateRandomWord(25));
        } else {
            tblEmpresa.setId(id);
            tblEmpresa.setCodigo(codigo);
        }
        tblEmpresa.setIdentificador(saiEmpresa.getIdentificacao());
        tblEmpresa.setNome(saiEmpresa.getNome());
        tblEmpresa.setAtivo(b.voltaDados(saiEmpresa.getAtivo()));
        return tblEmpresa;
    }

    public SaiEmpresa salvar(SaiEmpresa saiEmpresa , String Tipo , String id){
        schemaIdentificationService.setSchemaByEntity(TblEmpresa.class); 
        
        if ("post".equals(Tipo)) {
            TblEmpresa tblEmpresa = converter(saiEmpresa, Tipo, null , null);
            jpaTblEmpresa.save(tblEmpresa);
            
            // Log da operação para auditoria
                
            return converter(tblEmpresa);
        } else {
            // Operação de atualização (patch)
            TblEmpresa tblEmpresaExistente = jpaTblEmpresa.findByCodigo(id);
            if (tblEmpresaExistente != null) {
                // Mantém o ID original
                TblEmpresa tblEmpresaAtualizada = converter(saiEmpresa, Tipo, tblEmpresaExistente.getId() , id);
                tblEmpresaAtualizada.setId(tblEmpresaExistente.getId());
                tblEmpresaAtualizada.setCodigo(id); // Mantém o código original
                
                jpaTblEmpresa.save(tblEmpresaAtualizada);
                
                  
                return converter(tblEmpresaAtualizada);
            }
            return null;
        }
    }

    public SaiEmpresa buscarPorId(String id){
        schemaIdentificationService.setSchemaByEntity(VW_UserEmpresa.class); 
        Long userId = userContext.getUserId() != null ? Long.valueOf( userContext.getUserId() ) : null;
        VW_UserEmpresa vwEmpresa = jpaVW_UserEmpresa.findByCodigoAndUserId(id, userId);
        if (vwEmpresa != null){
            return converter(vwEmpresa);
        } else {
            return null;
        }
    }

    public void deletar(String id){
        
        schemaIdentificationService.setSchemaByEntity(TblEmpresa.class); 

        TblEmpresa tblEmpresa = jpaTblEmpresa.findByCodigo(id);
        if (tblEmpresa != null){
            jpaTblEmpresa.delete(tblEmpresa);
            
             
        }
    }

    public List<SaiEmpresa> buscarTodos(){
        schemaIdentificationService.setSchemaByEntity(VW_UserEmpresa.class); 
        Long userId = userContext.getUserId() != null ? Long.valueOf( userContext.getUserId() ) : null;
        List<VW_UserEmpresa> listaTblEmpresa = jpaVW_UserEmpresa.findByUserId(userId);

        return listaTblEmpresa.stream().map(this::converter).toList();
    }
}
