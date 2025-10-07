package com.cronossuite.cadastros.repository.rlcl.util.beans.aux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.repository.model.db.rlcl.util.TblCabecaLookups;
import com.cronossuite.cadastros.repository.model.db.rlcl.util.VwLookpusLinhas;
import com.cronossuite.cadastros.repository.rlcl.util.jpa.JpaTblCabecaLookups;
import com.cronossuite.cadastros.repository.rlcl.util.jpa.JpaVwLookpusLinhas;
import com.cronossuite.cadastros.service.SchemaIdentificationService;

@Component
public class AuxVwLooks {
    
    @Autowired
    JpaVwLookpusLinhas jpaVwLookpusLinhas;

    @Autowired
    JpaTblCabecaLookups jpaTblCabecaLookups;
    
    @Autowired
    SchemaIdentificationService schemaIdentificationService;

    public VwLookpusLinhas LookLinha(String codeCabe, String codeLinha) {
         schemaIdentificationService.setSchemaByEntity(VwLookpusLinhas.class);
        return jpaVwLookpusLinhas.findByCodecabecaAndCodelinha(codeCabe, codeLinha);
    }

    public VwLookpusLinhas LookLinha(Long codeCabe, Long codeLinha) {
        schemaIdentificationService.setSchemaByEntity(VwLookpusLinhas.class);
        return jpaVwLookpusLinhas.findByIdAndIdcabeca( codeLinha ,codeCabe);
    }

    public TblCabecaLookups CabecaLook(String codeCabe) {
        schemaIdentificationService.setSchemaByEntity(TblCabecaLookups.class);
        return jpaTblCabecaLookups.findByCodigo(codeCabe) ;
    }
}
