package com.cronossuite.cadastros.repository.rlcl.empresa.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cronossuite.cadastros.repository.model.db.rlcl.empresa.TblEmpresa;

@Repository
public interface JpaTblEmpresa extends JpaRepository<TblEmpresa, Long> {
    TblEmpresa findByCodigo(String codigo);
    
}
