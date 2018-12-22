package br.com.pauta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pauta.converter.AssociadoConverter;
import br.com.pauta.dto.AssociadoInput;
import br.com.pauta.dto.AssociadoOutput;
import br.com.pauta.service.AssociadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("associado")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private AssociadoConverter associadoConverter;

	@GetMapping("/")
	@ApiOperation(value = "Carregar todos os associados", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Associados carregados com sucesso", response = AssociadoOutput.class, responseContainer = "List")
	})
	public List<AssociadoOutput> listarTodosAssociados() {
		return associadoConverter.toArray(associadoService.listarAssociados());
	}

	@ApiOperation(value = "Carregar um associado baseado no cpf", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Associado carregado com sucesso", response = AssociadoOutput.class),
		@ApiResponse(code = 404, message = "Associado referente ao cpf informado não existe")
	})
	@GetMapping("/{cpf}")
	public AssociadoOutput carregarAssociado(@ApiParam(value = "String", name = "cpf", required = true) @PathVariable("cpf") String cpf) {
		return associadoConverter.toOutput(associadoService.carregarAssociado(cpf));
	}

	@ApiOperation(value = "Cria novo associado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Novo associado criado", response = AssociadoOutput.class)
	})
	@PostMapping("/")
	public AssociadoOutput cadastrarAssociado(
			@ApiParam(value = "AssociadoInput", name = "associadoInput", required = true) @RequestBody AssociadoInput associadoInput) {
		return associadoConverter
				.toOutput(associadoService.cadastrarAssociado(associadoConverter.toEntity(associadoInput)));
	}
}
