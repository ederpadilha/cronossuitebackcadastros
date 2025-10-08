package com.cronossuite.cadastros.repository.rlcl.usuario.saida;
import java.io.Serializable;

import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaiUsers implements Serializable  {
    private static final long serialVersionUID = 1L;
    
      
     private String login ;    
     private String password ;      
     private SaiPessoa pessoa ;  
     private Boolean ativo ;
}