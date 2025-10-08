package com.cronossuite.cadastros.repository.model.db.rlcl.empresa;

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
@Entity(name = "rlclempre")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TblEmpresa implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_pk_rlclempre")
    @SequenceGenerator(sequenceName = "SEQ_pk_rlclempre", allocationSize = 1, name = "SEQ_pk_rlclempre")
	@Column(name = "id___empre")
    private long id ; 
    @Column(name = "ativoempre")
    private long ativo ; 
    @Column(name = "codigempre")
    private String codigo; 
    @Column(name = "idendempre")
    private String identificador; 
    @Column(name = "nome_empre")
    private String nome; 
    @Column(name = "id___pesso")
    private Long pessoa;
}
