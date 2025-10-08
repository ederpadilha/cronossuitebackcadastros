package com.cronossuite.cadastros.repository.rlcl.empresa.saida;

import java.io.Serializable;

import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor 
public class SaiEmpresa implements Serializable  {
        
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String identificacao;
    private SaiPessoa pessoa;
    private Boolean ativo;
}
