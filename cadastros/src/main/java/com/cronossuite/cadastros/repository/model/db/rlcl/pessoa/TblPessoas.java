package com.cronossuite.cadastros.repository.model.db.rlcl.pessoa;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "rlclpesso")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TblPessoas implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_RLCLPESSO")
    @SequenceGenerator(sequenceName = "SEQ_PK_RLCLPESSO", allocationSize = 1, name = "SEQ_PK_RLCLPESSO")
	@Column(name = "ID___PESSO")
    private Long id ; 
    @Column(name = "CODI_PESSO")
    private String codigo;  
    @Column(name = "NOME_PESSO")
    private String nome; 
    @Column(name = "NUME_PESSO")
    private String numero; 
    @Column(name = "ATIVOPESSO")
    private Long ativo; 
 
    @Column(name = "ID_T_UTLOK")
    private Long cabtipopessoa; 
    @Column(name = "ID_T_UTLOM")
    private Long lintipopessoa; 
    
}
