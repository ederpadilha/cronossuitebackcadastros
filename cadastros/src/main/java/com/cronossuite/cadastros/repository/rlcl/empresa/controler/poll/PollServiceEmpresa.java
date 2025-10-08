package com.cronossuite.cadastros.repository.rlcl.empresa.controler.poll;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cronossuite.cadastros.repository.rlcl.empresa.saida.SaiEmpresa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



@Tag(name = "Crud Empresa", description = "CRUD de Empresa")
public interface PollServiceEmpresa {
 

    @Operation(summary = "Obter todas as Empresas", description = "Retorna uma lista de todas as Empresas cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de Empresas retornada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = SaiEmpresa.class))))
    public ResponseEntity<Object> ReadAll();

    @Operation(summary = "Obter Empresa por ID", description = "Retorna os detalhes de uma Empresa específica com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Empresa retornada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SaiEmpresa.class)))
    public ResponseEntity<Object> ReadById(@NotNull String id);

    @Operation(summary = "Criar uma nova Empresa", description = "Adiciona uma nova Empresa ao sistema com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SaiEmpresa.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para criar a Empresa.")
    @ApiResponse(responseCode = "409", description = "Conflito ao criar a Empresa (por exemplo, Empresa já existe).")
    public ResponseEntity<Object> Create(@Valid SaiEmpresa pessoa);

    @Operation(summary = "Atualizar uma Empresa existente", description = "Atualiza os dados de uma Empresa existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SaiEmpresa.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para atualizar a Empresa.")
    @ApiResponse(responseCode = "404", description = "Empresa não encontrada com o ID fornecido.")
    public ResponseEntity<Object> Update(@Valid SaiEmpresa pessoa , @NotNull String id);

    @Operation(summary = "Deletar uma Empresa", description = "Remove uma Empresa do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Empresa não encontrada com o ID fornecido.")
    public ResponseEntity<Object> Delete(@NotNull String id);
}
