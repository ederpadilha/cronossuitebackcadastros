package com.cronossuite.cadastros.repository.model.db.rlcl.util;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity(name = "VW_utlomutlok")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VwLookpusLinhas implements Serializable  {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id___utlom")
    private Long id ;
    @Column(name = "ID___UTLOK")
    private Long idcabeca ; 
    @Column(name = "CODE_UTLOK")
    private String codecabeca ;
    @Column(name = "NOME_UTLOK")
    private String nomecabeca ;

    @Column(name = "CODE_utlom")
    private String codelinha ;
    @Column(name = "NOME_utlom")
    private String nomelinha ;

    @Column(name = "ATIVOUTLOK")
    private Long ativo ;


 
    
}
