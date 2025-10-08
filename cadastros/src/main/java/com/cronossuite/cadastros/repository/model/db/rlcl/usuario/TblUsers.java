package com.cronossuite.cadastros.repository.model.db.rlcl.usuario;
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

@Entity(name = "rlclusers")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TblUsers  implements Serializable  {
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_RLCLUSERS")
    @SequenceGenerator(sequenceName = "SEQ_PK_RLCLUSERS", allocationSize = 1, name = "SEQ_PK_RLCLUSERS")
	@Column(name = "ID___USERS")
    private Long id ; 
    @Column(name = "LOGINUSERS")
    private String login ;    
    @Column(name = "PASSWUSERS")
    private String password ;      
    @Column(name = "ID___PESSO")
    private Long pessoa ;  
    @Column(name = "ATIVOUSERS")    
    private Long ativo ;
}
