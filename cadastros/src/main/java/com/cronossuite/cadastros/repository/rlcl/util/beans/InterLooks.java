package com.cronossuite.cadastros.repository.rlcl.util.beans;

import java.util.List;

import com.cronossuite.cadastros.repository.model.db.rlcl.util.TblCabecaLookups;
import com.cronossuite.cadastros.repository.model.db.rlcl.util.VwLookpusLinhas;

public interface InterLooks {
    
    List<VwLookpusLinhas> ListaLooks( String codeCabe ); 
    VwLookpusLinhas LookLinha( String codeCabe , String codeLinha );
    VwLookpusLinhas LookLinha( Long idCabe , Long idLinha );
    TblCabecaLookups CabecaLook( String codeCabe );
}
