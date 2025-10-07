package com.cronossuite.cadastros.repository.model.db.rlcl.util;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity(name = "RLCLUTLOK")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TblCabecaLookups implements Serializable  {
     private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_RLCLUTLOK")
    @SequenceGenerator(sequenceName = "SEQ_PK_RLCLUTLOK", allocationSize = 1, name = "SEQ_PK_RLCLUTLOK")
	@Column(name = "ID___UTLOK")
    private Long id ;
    @Column(name = "CODE_UTLOK")
    private String codigo; 
    @Column(name = "NOME_UTLOK")
    private String descricao;

    @Column(name = "ATIVOUTLOK")
    private Long ativo; 


}