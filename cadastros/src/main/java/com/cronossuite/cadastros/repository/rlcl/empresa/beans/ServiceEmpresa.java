package com.cronossuite.cadastros.repository.rlcl.empresa.beans;

import java.util.List;

import com.cronossuite.cadastros.repository.rlcl.empresa.saida.SaiEmpresa;

public interface  ServiceEmpresa {
    List<SaiEmpresa> ReadAll();
    SaiEmpresa ReadById(String id);
    SaiEmpresa Create(SaiEmpresa pessoa);
    SaiEmpresa Update(SaiEmpresa pessoa , String id);
    void Delete(String id);
}
