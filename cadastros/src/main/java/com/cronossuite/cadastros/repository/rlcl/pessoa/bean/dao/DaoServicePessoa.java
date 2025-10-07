package com.cronossuite.cadastros.repository.rlcl.pessoa.bean.dao;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.repository.rlcl.pessoa.bean.ServicePessoa;
import com.cronossuite.cadastros.repository.rlcl.pessoa.bean.aux.AuxSaiPessoa;
import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;


@Service
public class DaoServicePessoa implements ServicePessoa{

    @Autowired
    AuxSaiPessoa pessoa ; 


    @Override
    public List<SaiPessoa> ReadAll() {
         return   pessoa.listarTodasPessoas() ;
    }

    @Override
    public SaiPessoa ReadById(String id) {
        return pessoa.buscarPorCodigo(id);
    }

    @Override
    public SaiPessoa Create(SaiPessoa pess) {
        return pessoa.Salvar("post", pess, null);
    }

    @Override
    public SaiPessoa Update(SaiPessoa pess, String id) {
         return pessoa.Salvar("patch", pess, id);
    }

    @Override
    public void Delete(String id) {
        pessoa.deletarPessoa(id);
         
    }
    
}
