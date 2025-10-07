package com.cronossuite.cadastros.controller.aux;

import org.springframework.http.ResponseEntity;

public class AuxControler {
    public ResponseEntity<Object> getResposta( Object ob  ){
        if (ob == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(ob);   
        }
    }
}
