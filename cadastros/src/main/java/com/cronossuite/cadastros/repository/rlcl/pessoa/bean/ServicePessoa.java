package com.cronossuite.cadastros.repository.rlcl.pessoa.bean;

import java.util.List;

import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;

public interface ServicePessoa {
 
    List<SaiPessoa> ReadAll();
    SaiPessoa ReadById(String id);
    SaiPessoa Create(SaiPessoa pessoa);
    SaiPessoa Update(SaiPessoa pessoa , String id);
    void Delete(String id);

}
