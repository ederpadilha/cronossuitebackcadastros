package com.cronossuite.cadastros.repository.rlcl.pessoa.bean.aux;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.annotation.UseSchema;
import com.cronossuite.cadastros.repository.model.db.rlcl.pessoa.TblPessoas;
import com.cronossuite.cadastros.repository.model.db.rlcl.util.VwLookpusLinhas;
import com.cronossuite.cadastros.repository.rlcl.pessoa.enums.eLookPessoaCode;
import com.cronossuite.cadastros.repository.rlcl.pessoa.jpa.JpaTblPessoas;
import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;
import com.cronossuite.cadastros.repository.rlcl.util.beans.InterLooks;
import com.cronossuite.cadastros.service.SchemaIdentificationService;
import com.cronossuite.cadastros.utilis.U_bolleans;

@Component
public class AuxSaiPessoa {

   
    
    @Autowired
    JpaTblPessoas jpaTblPessoas;
    @Autowired
    InterLooks interLooks ;
    
    String stringTipoPessoa = eLookPessoaCode.TIPO_PESSOA.Chave;

 

    @Autowired
    SchemaIdentificationService schemaIdentificationService;

    U_bolleans b = new U_bolleans();

    public SaiPessoa converter(TblPessoas tblPessoas) {
        if (tblPessoas == null) {
            return null;
        }
        SaiPessoa sai = new SaiPessoa();
        sai.setId(tblPessoas.getCodigo());
        sai.setNome(tblPessoas.getNome());
        sai.setNumero(tblPessoas.getNumero());
        sai.setAtivo(b.voltaDados(tblPessoas.getAtivo()));
        VwLookpusLinhas look = interLooks.LookLinha(tblPessoas.getCabtipopessoa(), tblPessoas.getLintipopessoa());
        sai.setTipocode(look.getCodelinha());
        sai.setTipo(look.getNomelinha());
        return sai;
    }
    
    public TblPessoas converter(SaiPessoa saiPessoa , Long id) {
        if (saiPessoa == null) {
            return null;
        }
        TblPessoas tbl = new TblPessoas();
        Long ativo = b.voltaDados(saiPessoa.getAtivo());
        if (id != null)  tbl.setId(id);
        tbl.setAtivo(ativo);
        tbl.setCodigo(saiPessoa.getId());
        tbl.setNome(saiPessoa.getNome());
        tbl.setNumero(saiPessoa.getNumero());
        VwLookpusLinhas look = interLooks.LookLinha(stringTipoPessoa, saiPessoa.getTipocode());
        if (look != null) {
            tbl.setCabtipopessoa(look.getIdcabeca());
            tbl.setLintipopessoa(look.getId());
        }
        return tbl;
    }  

    public SaiPessoa Salvar( String tipo , SaiPessoa pessoa , String id) {
       // Define automaticamente o schema baseado na entidade
       schemaIdentificationService.setSchemaByEntity(TblPessoas.class);
       
       SaiPessoa ret = new SaiPessoa();
       if ("post".equals(tipo)) {
              TblPessoas tbl = converter(pessoa , null);
              TblPessoas salvo = salvarPessoa(tbl);
              ret = converter(salvo);
        } else if ("patch".equals(tipo)) {
            TblPessoas existente = buscartblPorcodigo(id);
            if (existente != null) {
                TblPessoas tblAtualizado = converter(pessoa, existente.getId());
                TblPessoas salvo = salvarPessoa(tblAtualizado);
                ret = converter(salvo);  
            }  
       }
       return ret;
    }

    @UseSchema("rlcl") // Força uso do schema RLCL (Cadastros Gerais)
    public List<SaiPessoa> listarTodasPessoas() {
        List<TblPessoas> tblPessoasList = jpaTblPessoas.findAll();
        return tblPessoasList.stream()
                .map(this::converter)
                .collect(Collectors.toList());
    }

    public Optional<TblPessoas> buscarPorId(Long id) {
        schemaIdentificationService.setSchemaByEntity(TblPessoas.class);
        return jpaTblPessoas.findById(id);
    }

    private TblPessoas buscartblPorcodigo(String codigo) {
        return jpaTblPessoas.findByCodigo(codigo);
    }

    public SaiPessoa buscarPorCodigo(String codigo) {
        schemaIdentificationService.setSchemaByEntity(TblPessoas.class);
        return converter(buscartblPorcodigo(codigo));
    }

    public TblPessoas salvarPessoa(TblPessoas pessoa) {
        return jpaTblPessoas.save(pessoa);
    }

    public void deletarPessoa(String id) {
        schemaIdentificationService.setSchemaByEntity(TblPessoas.class);
        TblPessoas tbl = buscartblPorcodigo(id);
        if (tbl != null) {
            jpaTblPessoas.delete(tbl);
        }   
    }
}
