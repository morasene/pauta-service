package br.com.pauta.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pauta.converter.SessaoConverter;
import br.com.pauta.dto.SessaoInput;
import br.com.pauta.dto.SessaoOutput;
import br.com.pauta.dto.SessaoVotoOutput;
import br.com.pauta.service.SessaoBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("sessao/v1")
public class SessaoController {

	private final SessaoBusiness sessaoBusiness;
	private final SessaoConverter sessaoConverter;

	private SessaoController(SessaoBusiness sessaoBusiness, SessaoConverter sessaoConverter) {
		super();
		this.sessaoBusiness = sessaoBusiness;
		this.sessaoConverter = sessaoConverter;
	}

	@GetMapping("/")
	@ApiOperation(value = "Carregar todos as sessões", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Sessões carregadas com sucesso", response = SessaoOutput.class, responseContainer = "List") })
	public List<SessaoOutput> listarTodasSessoes() {
		return sessaoConverter.toArray(sessaoBusiness.listarSessoes());
	}

	@ApiOperation(value = "Carregar uma sessão baseada no identificador", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Sessão carregado com sucesso", response = SessaoOutput.class),
			@ApiResponse(code = 404, message = "Sessão referente ao identificador informado não existe") })
	@GetMapping("/{id}")
	public SessaoOutput carregarSessao(@ApiParam(value = "Integer", name = "id", required = true) @PathVariable("id") Integer id) {
		return sessaoConverter.toOutput(sessaoBusiness.carregarSessao(id));
	}

	@GetMapping("/{id}/votos")
	@ApiOperation(value = "Carregar todos os votos de uma Sessão", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Votos carregados com sucesso", response = SessaoVotoOutput.class, responseContainer = "List") })
	public SessaoVotoOutput listarTodasVotosDeUmaSessao(@ApiParam(value = "Integer", name = "id", required = true) @PathVariable("id") Integer id) {
		return sessaoConverter.toSessaoOutput(sessaoBusiness.listarVotosDaSessao(id));
	}

	@ApiOperation(value = "Cadastrar nova sessao", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Nova sessão criada", response = SessaoOutput.class) })
	@PostMapping("/")
	public SessaoOutput cadastrarSessao(@ApiParam(value = "SessaoInput", name = "sessaoInput", required = true) @RequestBody SessaoInput sessaoInput)
			throws Exception {
		return sessaoConverter.toOutput(sessaoBusiness.cadastrarSessao(sessaoConverter.toEntity(sessaoInput)));
	}
}
