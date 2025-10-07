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
@Entity(name = "RLCLUTLOM")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TblLinhaLookups implements Serializable  {
     private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_RLCLUTLOM")
    @SequenceGenerator(sequenceName = "SEQ_PK_RLCLUTLOM", allocationSize = 1, name = "SEQ_PK_RLCLUTLOM")
	
    @Column(name = "ID___UTLOM")
    private Long id ;
    @Column(name = "ID___UTLOK")
    private Long idcabecalho ;
    @Column(name = "CODE_UTLOM")
    private String codigo; 
    @Column(name = "NOME_UTLOM")
    private String descricao;

    @Column(name = "ATIVOUTLOM")
    private Long ativo; 


}