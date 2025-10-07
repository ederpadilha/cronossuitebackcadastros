package com.cronossuite.cadastros.repository.rlcl.util.saida;

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
public class SaiLooks implements Serializable  {
    
    
    private static final long serialVersionUID = 1L;
    
    private Long idCabecalho;
    private String codeCabecalho;
    private String nomeCabecalho;
    private Long idLinha;
    private String codeLinha;
    private String nomeLinha;
}
