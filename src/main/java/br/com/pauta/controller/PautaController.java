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

import br.com.pauta.converter.PautaConverter;
import br.com.pauta.dto.AssociadoOutput;
import br.com.pauta.dto.PautaInput;
import br.com.pauta.dto.PautaOutput;
import br.com.pauta.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@Autowired
	private PautaConverter pautaConverter;

	@GetMapping("/")
	@ApiOperation(value = "Carregar todos as pautas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Pautas carregadas com sucesso", response = AssociadoOutput.class, responseContainer = "List") })
	public List<PautaOutput> listarTodasPautas() {
		return pautaConverter.toArray(pautaService.listarPautas());
	}

	@ApiOperation(value = "Carregar uma pauta baseado no identificador", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Pauta carregado com sucesso", response = AssociadoOutput.class),
			@ApiResponse(code = 404, message = "Pauta referente ao identificador informado não existe")
	})
	@GetMapping(value = "/{id}")
	public PautaOutput carregarPauta(@ApiParam(value = "Integer", name = "id", required = true) @PathVariable("id") Integer id) {
		return pautaConverter.toOutput(pautaService.carregarPauta(id));
	}

	@ApiOperation(value = "Cadastrar nova pauta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Nova pauta criada", response = AssociadoOutput.class)
	})
	@PostMapping("/")
	public PautaOutput cadastrarAssociado(
			@ApiParam(value = "PautaInput", name = "pautaInput", required = true) @RequestBody PautaInput pautaInput) {
		return pautaConverter.toOutput(pautaService.cadastrarPauta(pautaConverter.toEntity(pautaInput)));
	}
}
