package com.cronossuite.cadastros.repository.rlcl.util.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cronossuite.cadastros.repository.model.db.rlcl.util.TblLinhaLookups;

 
public interface JpaTblLinhaLookups extends JpaRepository<TblLinhaLookups, Long> {
    List<TblLinhaLookups> findByIdcabecalho(Long idcabecalho);
    TblLinhaLookups findByIdcabecalhoAndId(Long idcabecalho, Long id);
    TblLinhaLookups findByIdcabecalhoAndCodigo(Long idcabecalho, String codigo);
    
}
