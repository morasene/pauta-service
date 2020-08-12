package br.com.pauta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pauta.entity.Pauta;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.repository.PautaRepository;

@Service
public class PautaService {

	private PautaRepository pautaRepository;

	private PautaService(PautaRepository pautaRepository) {
		super();
		this.pautaRepository = pautaRepository;
	}

	public Pauta cadastrarPauta(Pauta pauta) {
		return pautaRepository.save(pauta);
	}

	public Pauta carregarPauta(Integer id) {
		return pautaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada."));
	}

	public List<Pauta> listarPautas() {
		return pautaRepository.findAll();
	}
}
