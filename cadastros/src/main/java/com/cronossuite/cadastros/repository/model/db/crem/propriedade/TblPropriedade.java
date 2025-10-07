package com.cronossuite.cadastros.repository.model.db.crem.propriedade;
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
@Entity(name = "CREMPRPAG")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TblPropriedade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_CREMPRPAG")
    @SequenceGenerator(sequenceName = "SEQ_PK_CREMPRPAG", allocationSize = 1, name = "SEQ_PK_CREMPRPAG")
	@Column(name = "ID___PRPAG")
    private long id ; 
    @Column(name = "ATIVOPRPAG")
    private long ativo ; 
    @Column(name = "id___empre")
    private long empresa ;
    @Column(name = "CODE_PRPAG")
    private String codigo ;
    @Column(name = "NOME_PRPAG")
    private String nome ;
}

