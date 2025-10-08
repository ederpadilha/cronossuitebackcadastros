package com.cronossuite.cadastros.repository.rlcl.usuario.beans;

import java.util.List;

import com.cronossuite.cadastros.repository.rlcl.usuario.saida.SaiUsers;

public interface ServiceUsers {
     
    List<SaiUsers> ReadAll();
    SaiUsers ReadById(String id);
    SaiUsers Create(SaiUsers pessoa);
    SaiUsers Update(SaiUsers pessoa , String id);
    void Delete(String id);

}
