package com.cronossuite.cadastros.repository.rlcl.pessoa.controler.ctrl;

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
import com.cronossuite.cadastros.repository.rlcl.pessoa.bean.ServicePessoa;
import com.cronossuite.cadastros.repository.rlcl.pessoa.controler.poll.PollServicePessoa;
import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/cadastros/pessoa")
@SecurityRequirement(name = "Bearer Authentication")
public class CtrlPollServicePessoa implements PollServicePessoa{

    @Autowired
    ServicePessoa servicePessoa;

    

    private AuxControler auxCtr = new AuxControler();

    private ResponseEntity<Object> getResposta(Object ob) {
        return auxCtr.getResposta(ob);
    }

    @Override
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> ReadAll() {
         return getResposta(servicePessoa.ReadAll().stream().map(obj -> (Object) obj).toList());
    }

    @Override
    @GetMapping(path = "/{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> ReadById(@PathVariable(value = "idPessoa") @NotNull String id) {
        return getResposta((Object) servicePessoa.ReadById(id));
    }

    @Override
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Create(@Valid @RequestBody SaiPessoa corpo) {
        return getResposta((Object) servicePessoa.Create(corpo));
    }

    @Override
    @PatchMapping(path = "/{idPessoa}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Update(@Valid @RequestBody SaiPessoa corpo, @PathVariable(value = "idPessoa") @NotNull String idPessoa) {
        return getResposta((Object) servicePessoa.Update(corpo, idPessoa));
    }

    @Override
    @DeleteMapping(path = "/{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "idPessoa") @NotNull String idPessoa) {
        servicePessoa.Delete(idPessoa);
        return ResponseEntity.noContent().build();
    }

   
   
    
}
