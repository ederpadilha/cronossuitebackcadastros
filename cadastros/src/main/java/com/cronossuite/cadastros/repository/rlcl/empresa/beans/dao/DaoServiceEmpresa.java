package com.cronossuite.cadastros.repository.rlcl.empresa.beans.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.repository.rlcl.empresa.beans.ServiceEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.beans.aux.AuxServiceEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.saida.SaiEmpresa;

@Service
public class DaoServiceEmpresa implements ServiceEmpresa {

    @Autowired
    AuxServiceEmpresa auxServiceEmpresa;


    @Override
    public List<SaiEmpresa> ReadAll() {
        return auxServiceEmpresa.buscarTodos();
    }

    @Override
    public SaiEmpresa ReadById(String id) {
        return auxServiceEmpresa.buscarPorId(id);
    }

    @Override
    public SaiEmpresa Create(SaiEmpresa pessoa) {
        return auxServiceEmpresa.salvar(pessoa, "post", null);
    }

    @Override
    public SaiEmpresa Update(SaiEmpresa pessoa, String id) {
        return auxServiceEmpresa.salvar(pessoa, "patch", id);
    }

    @Override
    public void Delete(String id) {
        auxServiceEmpresa.deletar(id);
    }
    
}
