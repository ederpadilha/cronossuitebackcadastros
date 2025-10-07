package com.cronossuite.cadastros.repository.rlcl.pessoa.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cronossuite.cadastros.repository.model.db.rlcl.pessoa.TblPessoas;

@Repository
public interface JpaTblPessoas extends JpaRepository<TblPessoas, Long> {
    public TblPessoas findByCodigo(String codigo);
    
}
