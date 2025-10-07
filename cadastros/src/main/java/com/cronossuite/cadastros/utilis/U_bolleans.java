package com.cronossuite.cadastros.utilis;

public class U_bolleans {
    
    public long voltaDados(Boolean ativo){
        if (ativo != null && ativo) return 1;
        return 0; 
    }
    
    public Boolean voltaDados(long ativo){
        if (ativo == 1) return true;
        return false; 
    }

    // Método adicional para tratar valores null com valor padrão
    public long voltaDadosComPadrao(Boolean ativo, boolean valorPadrao) {
        if (ativo != null) {
            return ativo ? 1 : 0;
        }
        return valorPadrao ? 1 : 0;
    }
    
    // Método para verificar se um Boolean é true, tratando null como false
    public boolean isTrue(Boolean valor) {
        return valor != null && valor;
    }
}