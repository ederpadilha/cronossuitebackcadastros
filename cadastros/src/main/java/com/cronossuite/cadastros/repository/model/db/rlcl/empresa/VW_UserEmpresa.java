package com.cronossuite.cadastros.repository.model.db.rlcl.empresa;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "VW_RLCL_USER_EMPRE")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VW_UserEmpresa implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
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
    @Column(name = "loginusers")
    private String username;
    @Column(name = "id___users")
    private Long userId;
}
