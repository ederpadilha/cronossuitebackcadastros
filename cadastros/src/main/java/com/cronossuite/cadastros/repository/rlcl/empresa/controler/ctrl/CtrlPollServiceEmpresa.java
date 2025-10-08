package com.cronossuite.cadastros.repository.rlcl.empresa.controler.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cronossuite.cadastros.controller.aux.AuxControler;
import com.cronossuite.cadastros.repository.rlcl.empresa.beans.ServiceEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.controler.poll.PollServiceEmpresa;
import com.cronossuite.cadastros.repository.rlcl.empresa.saida.SaiEmpresa;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/cadastros/empresa")
@SecurityRequirement(name = "Bearer Authentication")
public class CtrlPollServiceEmpresa implements PollServiceEmpresa{

    @Autowired
    ServiceEmpresa serviceEmpresa;

    

    private final AuxControler auxCtr = new AuxControler();

    private ResponseEntity<Object> getResposta(Object ob) {
        return auxCtr.getResposta(ob);
    }

    @Override
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> ReadAll() {
         return getResposta(serviceEmpresa.ReadAll().stream().map(obj -> (Object) obj).toList());
    }

    @Override
    @GetMapping(path = "/{idEmpresa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> ReadById(@PathVariable(value = "idEmpresa") @NotNull String id) {
        return getResposta((Object) serviceEmpresa.ReadById(id));
    }

    @Override
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Create(@Valid @RequestBody SaiEmpresa corpo) {
        return getResposta((Object) serviceEmpresa.Create(corpo));
    }

    @Override
    @PatchMapping(path = "/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Update(@Valid @RequestBody SaiEmpresa corpo, @PathVariable(value = "idEmpresa") @NotNull String idEmpresa) {
        return getResposta((Object) serviceEmpresa.Update(corpo, idEmpresa));
    }

    @Override
    @DeleteMapping(path = "/{idEmpresa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "idEmpresa") @NotNull String idEmpresa) {
        serviceEmpresa.Delete(idEmpresa);
        return ResponseEntity.noContent().build();
    }

   
   
    
}
