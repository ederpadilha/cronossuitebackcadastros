package com.cronossuite.cadastros.repository.rlcl.empresa.jpa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cronossuite.cadastros.repository.model.db.rlcl.empresa.VW_UserEmpresa;

@Repository
public interface JpaVW_UserEmpresa  extends JpaRepository<VW_UserEmpresa, Long> {

    VW_UserEmpresa findByCodigoAndUserId(String codigo, Long userId);
    List<VW_UserEmpresa> findByUserId(Long userId);
}
