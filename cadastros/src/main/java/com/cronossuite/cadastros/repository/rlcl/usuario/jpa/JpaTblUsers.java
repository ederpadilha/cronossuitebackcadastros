package com.cronossuite.cadastros.repository.rlcl.usuario.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cronossuite.cadastros.repository.model.db.rlcl.usuario.TblUsers;

@Repository
public interface JpaTblUsers  extends JpaRepository<TblUsers, Long> {
    TblUsers findByLogin(String login);
}
