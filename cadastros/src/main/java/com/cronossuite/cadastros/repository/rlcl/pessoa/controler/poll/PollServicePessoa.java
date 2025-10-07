package com.cronossuite.cadastros.repository.rlcl.pessoa.controler.poll;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cronossuite.cadastros.repository.rlcl.pessoa.saida.SaiPessoa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



@Tag(name = "Crud Pessoa", description = "CRUD de Pessoa")
public interface PollServicePessoa {
 

    @Operation(summary = "Obter todas as Pessoas", description = "Retorna uma lista de todas as Pessoas cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de Pessoas retornada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = SaiPessoa.class))))
    public ResponseEntity<Object> ReadAll();
    
    @Operation(summary = "Obter Pessoa por ID", description = "Retorna os detalhes de uma Pessoa específica com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Pessoa retornada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE  , schema = @Schema(implementation = SaiPessoa.class)))
    public ResponseEntity<Object> ReadById(@NotNull String id);

    @Operation(summary = "Criar uma nova Pessoa", description = "Adiciona uma nova Pessoa ao sistema com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SaiPessoa.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para criar a Pessoa.")
    @ApiResponse(responseCode = "409", description = "Conflito ao criar a Pessoa (por exemplo, Pessoa já existe).")
    public ResponseEntity<Object> Create(@Valid SaiPessoa pessoa);

    @Operation(summary = "Atualizar uma Pessoa existente", description = "Atualiza os dados de uma Pessoa existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SaiPessoa.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para atualizar a Pessoa.")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada com o ID fornecido.")
    public ResponseEntity<Object> Update(@Valid SaiPessoa pessoa , @NotNull String id);

    @Operation(summary = "Deletar uma Pessoa", description = "Remove uma Pessoa do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada com o ID fornecido.")
    public ResponseEntity<Object> Delete(@NotNull String id);
}
