package br.com.pauta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.pauta.entity.Associado;
import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;
import br.com.pauta.enumeration.VotoEnum;
import br.com.pauta.exception.BusinessException;
import br.com.pauta.repository.VotoRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class VotoServiceTests {


	@InjectMocks
	private VotoService votoService;

	@Mock
	private VotoRepository votoRepository;

	@Mock
	private AssociadoService associadoService;
	
	@Mock
	private SessaoService sessaoService;
	
	@Mock
	private Voto votoMock;

	private final Integer idVoto = 1;
	private final Integer idSessao = 1;
	private final Integer idAssociado = 1;

	@Test
	public void validarPresenteCarregarVotoID() {
		when(votoRepository.findById(idVoto)).thenReturn(Optional.of(votoMock));
		Optional<Voto> voto = votoService.carregarVoto(idVoto);
		assertTrue(voto.isPresent());
	}
	
	@Test
	public void validarCarregarVotoID() {
		when(votoRepository.findById(idVoto)).thenReturn(Optional.of(votoMock));
		Optional<Voto> voto = votoService.carregarVoto(idVoto);
		assertEquals(voto.get(), votoMock);
	}
	
	@Test
	public void validarListarVotos() {
		when(votoRepository.findAll()).thenReturn(Collections.emptyList());
		List<Voto> listarVotos = votoService.listarVotos();
		assertTrue(listarVotos.size() == 0);
	}
	
	@Test
	public void validarCadastrarVotoAssociadoJaVotou() throws Exception {
		Voto voto = new Voto();
		voto.setIdSessao(idSessao);
		voto.setIdAssociado(idAssociado);
		//
		Voto votoMock = new Voto();
		Associado associado = new Associado();
		associado.setIdAssociado(idAssociado);
		votoMock.setAssociado(associado);
//		when(votoRepository.findBySessaoIdSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		try {
			votoService.cadastrarVoto(voto);			
		} catch(BusinessException e) {			
			assertEquals("Associado ja efetuou o voto para esta pauta.", e.getMessage());
		}
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
		
		//when(votoRepository.findBySessaoIdSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		//when(associadoService.carregarAssociado(idAssociado)).thenReturn(new Associado());
		//when(sessaoService.carregarSessao(idSessao)).thenReturn(sessao);
		try {
			votoService.cadastrarVoto(voto);			
		} catch(BusinessException e) {			
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
		Associado associado = new Associado();
		associado.setIdAssociado(2);
		votoMock.setAssociado(associado);
		//
		Pauta pauta = new Pauta();
		pauta.setQuantidadeVotosNao(0);
		pauta.setQuantidadeVotosSim(0);
		Sessao sessao = new Sessao();
		sessao.setIdSessao(idSessao);
		sessao.setPauta(pauta);
		sessao.setDataFim(LocalDateTime.now().plusMinutes(10));
		
		//when(votoRepository.findBySessaoIdSessao(idSessao)).thenReturn(Collections.singletonList(votoMock));
		//when(associadoService.carregarAssociado(idAssociado)).thenReturn(new Associado());
		//when(sessaoService.carregarSessao(idSessao)).thenReturn(sessao);
		when(votoRepository.save(voto)).thenReturn(voto);
		
		Voto votoNovo = votoService.cadastrarVoto(voto);
		assertEquals(votoNovo, voto);
		
	}
	@Test
	public void validarListarVotosDaSessao() {
		when(votoRepository.findBySessaoIdSessao(idVoto)).thenReturn(Collections.emptyList());
		List<Voto> votos = votoService.listarVotosPorSessao(idVoto);
		assertTrue(votos.size() == 0);
	}
}
