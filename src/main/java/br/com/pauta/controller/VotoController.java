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

import br.com.pauta.converter.VotoConverter;
import br.com.pauta.dto.SessaoOutput;
import br.com.pauta.dto.VotoInput;
import br.com.pauta.dto.VotoOutput;
import br.com.pauta.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("voto")
public class VotoController {

	@Autowired
	private VotoService votoService;

	@Autowired
	private VotoConverter votoConverter;

	@GetMapping("/")
	@ApiOperation(value = "Carregar todos os votos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Votos carregados com sucesso", response = SessaoOutput.class, responseContainer = "List") })
	public List<VotoOutput> listarTodosVotos() {
		return votoConverter.arrayEntitytoArrayDTOOutput(votoService.listarVotos());
	}

	@ApiOperation(value = "Carregar um voto baseada no identificador", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Voto carregado com sucesso", response = SessaoOutput.class),
			@ApiResponse(code = 404, message = "Voto referente ao identificador informado não existe") })
	@GetMapping("/{id}")
	public VotoOutput carregarVoto(
			@ApiParam(value = "Integer", name = "id", required = true) @PathVariable("id") Integer id) {
		return votoConverter.entityToDTOOutput(votoService.carregarVoto(id));
	}

	@ApiOperation(value = "Cadastrar novo voto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Novo voto criado", response = SessaoOutput.class) })
	@PostMapping("/")
	public VotoOutput cadastrarVoto(
			@ApiParam(value = "VotoInput", name = "votoInput", required = true) @RequestBody VotoInput votoInput)
			throws Exception {
		return votoConverter.entityToDTOOutput(votoService.cadastrarVoto(votoConverter.dtoInputToEntity(votoInput)));
	}
}
