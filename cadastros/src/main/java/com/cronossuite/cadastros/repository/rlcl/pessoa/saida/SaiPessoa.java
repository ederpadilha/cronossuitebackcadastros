package com.cronossuite.cadastros.repository.rlcl.pessoa.saida;

import java.io.Serializable;

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

public class SaiPessoa implements Serializable  {
    
    
    private static final long serialVersionUID = 1L;
    private String  id;
    private String  numero;
    private String  nome;
    private Boolean ativo;
    private String  tipocode;
    private String  tipo;
}
