package com.cronossuite.cadastros.repository.rlcl.usuario.beans.aux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.repository.model.db.rlcl.usuario.TblUsers;
import com.cronossuite.cadastros.repository.rlcl.usuario.jpa.JpaTblUsers;
import com.cronossuite.cadastros.repository.rlcl.usuario.saida.SaiUsers;
import com.cronossuite.cadastros.service.SchemaIdentificationService;
import com.cronossuite.cadastros.utilis.U_bolleans;

@Component
public class AuxServiceUsers {
    
    @Autowired
    JpaTblUsers jpaTblUsers;


    @Autowired
    SchemaIdentificationService schemaIdentificationService;

    U_bolleans b = new U_bolleans();

    private SaiUsers converter (TblUsers tblUsers){
        SaiUsers saiUsers = new SaiUsers();
        saiUsers.setAtivo(b.voltaDados(tblUsers.getAtivo()));
        saiUsers.setLogin(tblUsers.getLogin());
        saiUsers.setPassword("********");
        return saiUsers;
    }


    private TblUsers converter (SaiUsers saiUsers , Long id) {
        if (saiUsers == null) {
            return null;
        }
        TblUsers tbl = new TblUsers();
        Long ativo = b.voltaDados(saiUsers.getAtivo());
        if (id != null)  tbl.setId(id);
        tbl.setLogin(saiUsers.getLogin());
        tbl.setPassword(saiUsers.getPassword());
        tbl.setAtivo(ativo);
        return tbl;
    }

    public SaiUsers Create (SaiUsers saiUsers){
        schemaIdentificationService.setSchemaByEntity(TblUsers.class); 
        TblUsers tblUsers = converter(saiUsers , null);
        jpaTblUsers.save(tblUsers);
        return converter(tblUsers);
    }

    
  

}
