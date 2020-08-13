package br.com.pauta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import br.com.pauta.config.CacheConfig;
import br.com.pauta.entity.Associado;
import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;
import br.com.pauta.enumeration.VotoEnum;
import br.com.pauta.exception.BusinessException;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.vendor.client.UserClient;
import br.com.pauta.vendor.dto.StatusVote;
import br.com.pauta.vendor.dto.UserDTO;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class VotoBusinessTests {

	@InjectMocks
	private VotoBusiness votoBusiness;

	@Mock
	private VotoService votoService;

	@Mock
	private AssociadoService associadoService;

	@Mock
	private SessaoService sessaoService;

	@Mock
	private Voto votoMock;

	@Mock
	private UserClient userClient;

	@Mock
	private CacheConfig cacheConfig;

	private final Integer idVoto = 1;
	private final Integer idSessao = 1;
	private final Integer idAssociado = 1;

	@Test
	public void validarPresenteCarregarVotoID() {
		when(votoService.carregarVoto(idVoto)).thenReturn(Optional.of(votoMock));
		Optional<Voto> voto = votoBusiness.carregarVoto(idVoto);
		assertTrue(voto.isPresent());
	}

	@Test
	public void validarCarregarVotoID() {
		when(votoService.carregarVoto(idVoto)).thenReturn(Optional.of(votoMock));
		Optional<Voto> voto = votoBusiness.carregarVoto(idVoto);
		assertEquals(voto.get(), votoMock);
	}

	@Test
	public void validarListarVotos() {
		when(votoService.listarVotos()).thenReturn(Collections.emptyList());
		List<Voto> listarVotos = votoBusiness.listarVotos();
		assertTrue(listarVotos.size() == 0);
	}

	@Test
	public void validarCadastrarVotoAssociadoJaVotou() throws Exception {
		Voto voto = new Voto();
		voto.setIdSessao(idSessao);
		voto.setIdAssociado(idAssociado);
		//
		Voto votoMock = new Voto();
		Associado associado = criarAssociado();
		votoMock.setAssociado(associado);
		when(votoService.listarVotosPorSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		try {
			votoBusiness.cadastrarVoto(voto);
		} catch (BusinessException e) {
			assertEquals("Associado ja efetuou o voto para esta pauta.", e.getMessage());
		}
	}

	@Test(expected = ResourceNotFoundException.class)
	public void validarCadastrarVotoAssociadoResourceNotFoundException() {
		Voto voto = new Voto();
		voto.setIdSessao(idSessao);
		voto.setIdAssociado(idAssociado);
		voto.setVoto(VotoEnum.SIM);
		//
		Voto votoMock = new Voto();
		Associado associado = criarSegundoAssociado();
		votoMock.setAssociado(associado);
		when(cacheConfig.associadoCache()).thenReturn(associadoCache());
		when(votoService.listarVotosPorSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		when(associadoService.carregarAssociado(idAssociado)).thenThrow(new ResourceNotFoundException("a"));
		votoBusiness.cadastrarVoto(voto);
	}

	private UserDTO criarUserDTO() {
		UserDTO user = new UserDTO();
		user.setStatus(StatusVote.ABLE_TO_VOTE);
		return user;
	}

	@Test(expected = ResourceNotFoundException.class)
	public void validarCadastrarVotoSessaoNaoEncontrada() {
		Voto voto = new Voto();
		voto.setIdSessao(idSessao);
		voto.setIdAssociado(idAssociado);
		voto.setVoto(VotoEnum.SIM);
		//
		Voto votoMock = new Voto();
		Associado associado = criarSegundoAssociado();
		votoMock.setAssociado(associado);
		when(cacheConfig.associadoCache()).thenReturn(associadoCache());
		when(userClient.findByCpf(associado.getCpf())).thenReturn(criarUserDTO());
		when(votoService.listarVotosPorSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		when(associadoService.carregarAssociado(idAssociado)).thenReturn(associado);
		when(sessaoService.carregarSessao(idSessao)).thenThrow(new ResourceNotFoundException("a"));
		votoBusiness.cadastrarVoto(voto);
	}

	private Associado criarAssociado() {
		Associado associado = new Associado();
		associado.setCpf("12345678909");
		associado.setIdAssociado(idAssociado);
		return associado;
	}
	
	private Associado criarSegundoAssociado() {
		Associado associado = new Associado();
		associado.setCpf("12345678909");
		associado.setIdAssociado(2);
		return associado;
	}

	@Test
	public void validarCadastrarVotoValidadeSessao() throws Exception {
		Voto voto = new Voto();
		voto.setIdSessao(idSessao);
		voto.setIdAssociado(idAssociado);
		voto.setVoto(VotoEnum.SIM);
		//
		Voto votoMock = new Voto();
		Associado associado = new Associado();
		associado.setIdAssociado(2);
		votoMock.setAssociado(associado);
		//
		Sessao sessao = new Sessao();
		sessao.setIdSessao(idSessao);
		sessao.setDataFim(LocalDateTime.now().minusMinutes(1));

		when(cacheConfig.associadoCache()).thenReturn(associadoCache());
		when(userClient.findByCpf(associado.getCpf())).thenReturn(criarUserDTO());
		when(votoService.listarVotosPorSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		when(associadoService.carregarAssociado(idAssociado)).thenReturn(new Associado());
		when(sessaoService.carregarSessao(idSessao)).thenReturn(sessao);
		try {
			votoBusiness.cadastrarVoto(voto);
		} catch (BusinessException e) {
			assertEquals("Sessão expirada.", e.getMessage());
		}
	}

	@Test
	public void validarCadastrarAssociado() {
		Voto voto = new Voto();
		voto.setIdSessao(idSessao);
		voto.setIdAssociado(idAssociado);
		voto.setVoto(VotoEnum.SIM);
		//
		Voto votoMock = new Voto();
		Associado associado = criarSegundoAssociado();
		votoMock.setAssociado(associado);
		//
		Pauta pauta = new Pauta();
		pauta.setQuantidadeVotosNao(0);
		pauta.setQuantidadeVotosSim(0);
		Sessao sessao = new Sessao();
		sessao.setIdSessao(idSessao);
		sessao.setPauta(pauta);
		sessao.setDataFim(LocalDateTime.now().plusMinutes(10));

		when(cacheConfig.associadoCache()).thenReturn(associadoCache());
		when(userClient.findByCpf(associado.getCpf())).thenReturn(criarUserDTO());
		when(votoService.listarVotosPorSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		when(associadoService.carregarAssociado(idAssociado)).thenReturn(associado);
		when(sessaoService.carregarSessao(idSessao)).thenReturn(sessao);
		when(votoService.cadastrarVoto(voto)).thenReturn(voto);

		Voto votoNovo = votoBusiness.cadastrarVoto(voto);
		assertEquals(votoNovo, voto);

	}

	@Test
	public void validarListarVotosDaSessao() {
		when(votoService.listarVotosPorSessao(idVoto)).thenReturn(Collections.emptyList());
		List<Voto> votos = votoBusiness.listarVotosPorSessao(idVoto);
		assertTrue(votos.size() == 0);
	}

	private Cache<String, UserDTO> userCache() {
		return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(1).build();
	}

	private Cache<Integer, Associado> associadoCache() {
		return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(1).build();
	}
}
