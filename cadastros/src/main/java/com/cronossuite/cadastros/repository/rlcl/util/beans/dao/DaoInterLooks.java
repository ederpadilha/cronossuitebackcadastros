package com.cronossuite.cadastros.repository.rlcl.util.beans.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.repository.model.db.rlcl.util.TblCabecaLookups;
import com.cronossuite.cadastros.repository.model.db.rlcl.util.VwLookpusLinhas;
import com.cronossuite.cadastros.repository.rlcl.util.beans.InterLooks;
import com.cronossuite.cadastros.repository.rlcl.util.beans.aux.AuxVwLooks;


@Service
public class DaoInterLooks implements InterLooks{

    @Autowired
    AuxVwLooks auxVwLooks;

    @Override
    public List<VwLookpusLinhas> ListaLooks(String codeCabe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ListaLooks'");
    }

    @Override
    public VwLookpusLinhas LookLinha(String codeCabe, String codeLinha) {
        return auxVwLooks.LookLinha(codeCabe, codeLinha);
    }

    @Override
    public VwLookpusLinhas LookLinha(Long idCabe, Long idLinha) {
        return auxVwLooks.LookLinha(idCabe, idLinha);
    }

    @Override
    public TblCabecaLookups CabecaLook(String codeCabe) {
        return auxVwLooks.CabecaLook(codeCabe);
    }

}
