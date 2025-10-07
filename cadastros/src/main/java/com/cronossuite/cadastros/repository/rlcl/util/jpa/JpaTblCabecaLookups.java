package com.cronossuite.cadastros.repository.rlcl.util.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cronossuite.cadastros.repository.model.db.rlcl.util.TblCabecaLookups;

@Repository
public interface JpaTblCabecaLookups extends JpaRepository<TblCabecaLookups, Long> {
    TblCabecaLookups findByCodigo(String codigo);
    
    
}
