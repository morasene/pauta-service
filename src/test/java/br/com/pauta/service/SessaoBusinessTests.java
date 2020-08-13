package br.com.pauta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.vendor.client.UserClient;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SessaoBusinessTests {

	@InjectMocks
	private SessaoBusiness sessaoBusiness;

	@Mock
	private VotoService votoService;
	
	@Mock
	private PautaService pautaService;
	
	@Mock
	private SessaoService sessaoService;
	
	@Mock
	private Sessao sessaoMock;

	@Mock
	private Pauta pautaMock;
	
	@Mock
	private UserClient userClient;

	private final Integer idSessao = 1;
	private final Integer idPauta = 1;

	@Test
	public void validarPresenteCarregarAssociadoID() {
		when(sessaoService.carregarSessao(idSessao)).thenReturn(sessaoMock);
		Sessao sessao = sessaoBusiness.carregarSessao(idSessao);
		assertTrue(sessao != null);
	}
	
	@Test
	public void validarCarregarAssociadoID() {
		when(sessaoService.carregarSessao(idSessao)).thenReturn(sessaoMock);
		Sessao sessao = sessaoBusiness.carregarSessao(idSessao);
		assertEquals(sessao, sessaoMock);
	}
	
	@Test
	public void validarListarAssociados() {
		when(sessaoService.listarSessoes()).thenReturn(Collections.emptyList());
		List<Sessao> listarSessoes = sessaoBusiness.listarSessoes();
		assertTrue(listarSessoes.size() == 0);
	}
	
	@Test
	public void validarCadastrarAssociado() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTempoEmMinutos(1);
		sessao.setIdPauta(idPauta);
		when(pautaService.carregarPauta(idPauta)).thenReturn(pautaMock);
		when(sessaoService.cadastrarSessao(sessao)).thenReturn(sessao);
		Sessao sessaoNova = sessaoBusiness.cadastrarSessao(sessao);
		assertEquals(sessaoNova, sessao);
	}
	
	@Test
	public void validarCadastrarAssociadoSemTempo() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setIdPauta(idPauta);
		when(pautaService.carregarPauta(idPauta)).thenReturn(pautaMock);
		when(sessaoService.cadastrarSessao(sessao)).thenReturn(sessao);
		Sessao sessaoNova = sessaoBusiness.cadastrarSessao(sessao);
		assertNotNull(sessaoNova.getDataFim());
	}
	
	@Test
	public void validarCadastrarAssociadoSemPauta() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setIdPauta(idPauta);
		sessaoBusiness.cadastrarSessao(sessao);			
	}

	@Test
	public void validarListarVotosDaSessao() {
		when(sessaoService.carregarSessao(idSessao)).thenReturn(sessaoMock);
		when(votoService.listarVotosPorSessao(idSessao)).thenReturn(Collections.emptyList());
		Sessao sessao = sessaoBusiness.listarVotosDaSessao(idSessao);
		assertEquals(sessao, sessaoMock);
	}

	@Test
	public void validarListarVotosDaSessaoSemSessao() {
		when(sessaoService.carregarSessao(idSessao)).thenThrow(new ResourceNotFoundException("Sessão não encontrada."));
		try {
			sessaoBusiness.listarVotosDaSessao(idSessao);
		} catch (ResourceNotFoundException e) {
			assertEquals("Sessão não encontrada.", e.getMessage());
		}
	}
}
