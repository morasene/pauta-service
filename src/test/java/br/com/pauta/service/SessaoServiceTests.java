package br.com.pauta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.repository.SessaoRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SessaoServiceTests {

	@InjectMocks
	private SessaoService sessaoService;

	@Mock
	private VotoService votoService;
	
	@Mock
	private PautaService pautaService;
	
	@Mock
	private SessaoRepository sessaoRepository;
	
	@Mock
	private Sessao sessaoMock;

	@Mock
	private Pauta pautaMock;

	private final Integer idSessao = 1;
	private final Integer idPauta = 1;

	@Test
	public void validarPresenteCarregarAssociadoID() {
		when(sessaoRepository.findById(idSessao)).thenReturn(Optional.of(sessaoMock));
		Sessao sessao = sessaoService.carregarSessao(idSessao);
		assertTrue(sessao != null);
	}
	
	@Test
	public void validarCarregarAssociadoID() {
		when(sessaoRepository.findById(idSessao)).thenReturn(Optional.of(sessaoMock));
		Sessao sessao = sessaoService.carregarSessao(idSessao);
		assertEquals(sessao, sessaoMock);
	}
	
	@Test
	public void validarListarAssociados() {
		when(sessaoRepository.findAll()).thenReturn(Collections.emptyList());
		List<Sessao> listarSessoes = sessaoService.listarSessoes();
		assertTrue(listarSessoes.size() == 0);
	}
	
	@Test
	public void validarCadastrarAssociado() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTempoEmMinutos(1);
		sessao.setIdPauta(idPauta);
		when(sessaoRepository.save(sessao)).thenReturn(sessao);
		Sessao sessaoNova = sessaoService.cadastrarSessao(sessao);
		assertEquals(sessaoNova, sessao);
	}
	
	@Test
	public void validarCadastrarAssociadoSemPauta() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setIdPauta(idPauta);
		sessaoService.cadastrarSessao(sessao);			
	}
}
