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

import br.com.pauta.entity.Associado;
import br.com.pauta.repository.AssociadoRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AssociadoServiceTests {

	@InjectMocks
	private AssociadoService associadoService;

	@Mock
	private AssociadoRepository associadoRepository;
	
	@Mock
	private Associado associadoMock;

	@Test
	public void validarCarregarAssociadoCpf() {
		String cpf = "12345678909";
		when(associadoRepository.findByCpf(cpf)).thenReturn(associadoMock);
		Associado associado = associadoService.carregarAssociado(cpf);
		assertEquals(associado, associadoMock);
	}

	@Test
	public void validarExisteCarregarAssociadoID() {
		Integer id = 1;
		when(associadoRepository.findById(id)).thenReturn(Optional.of(associadoMock));
		Optional<Associado> associado = associadoService.carregarAssociado(id);
		assertTrue(associado.isPresent());
	}
	
	@Test
	public void validarCarregarAssociadoID() {
		Integer id = 1;
		when(associadoRepository.findById(id)).thenReturn(Optional.of(associadoMock));
		Optional<Associado> associado = associadoService.carregarAssociado(id);
		assertEquals(associado.get(), associadoMock);
	}
	
	@Test
	public void validarListarAssociados() {
		when(associadoRepository.findAll()).thenReturn(Collections.emptyList());
		List<Associado> listarAssociados = associadoService.listarAssociados();
		assertTrue(listarAssociados.size() == 0);
	}

	@Test
	public void validarCadastrarAssociado() {
		when(associadoRepository.save(associadoMock)).thenReturn(associadoMock);
		Associado associado = associadoService.cadastrarAssociado(associadoMock);
		assertEquals(associado, associadoMock);
	}
}
