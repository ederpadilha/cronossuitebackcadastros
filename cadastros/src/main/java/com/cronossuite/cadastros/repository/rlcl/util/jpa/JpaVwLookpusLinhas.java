package com.cronossuite.cadastros.repository.rlcl.util.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cronossuite.cadastros.repository.model.db.rlcl.util.VwLookpusLinhas;

@Repository
public interface JpaVwLookpusLinhas extends JpaRepository<VwLookpusLinhas, Long> {
      List<VwLookpusLinhas> findByCodecabeca(String codecabeca);
      VwLookpusLinhas findByCodecabecaAndCodelinha(String codecabeca, String codelinha);    
      VwLookpusLinhas findByIdAndIdcabeca(Long id, Long idcabeca);
      
}
