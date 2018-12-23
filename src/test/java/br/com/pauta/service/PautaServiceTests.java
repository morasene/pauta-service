package br.com.pauta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import br.com.pauta.repository.PautaRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PautaServiceTests {

	@InjectMocks
	private PautaService pautaService;

	@Mock
	private PautaRepository pautaRepository;

	@Mock
	private Pauta pautaMock;

	@Test
	public void validarCarregarAssociadoID() {
		Integer id = 1;
		when(pautaRepository.findById(id)).thenReturn(null);
		Optional<Pauta> pauta = pautaService.carregarPauta(id);
		assertNull(pauta);
	}

	@Test
	public void validarListarAssociados() {
		when(pautaRepository.findAll()).thenReturn(Collections.emptyList());
		List<Pauta> listarPautas = pautaService.listarPautas();
		assertTrue(listarPautas.size() == 0);
	}

	@Test
	public void validarCadastrarAssociado() {
		when(pautaRepository.save(pautaMock)).thenReturn(pautaMock);
		Pauta pauta = pautaService.cadastrarPauta(pautaMock);
		assertEquals(pauta, pautaMock);
	}
}
