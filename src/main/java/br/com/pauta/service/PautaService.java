package br.com.pauta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pauta.entity.Pauta;
import br.com.pauta.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	public Pauta cadastrarPauta(Pauta pauta) {
		return pautaRepository.save(pauta);
	}

	public Optional<Pauta> carregarPauta(Integer id) {
		return pautaRepository.findById(id);
	}

	public List<Pauta> listarPautas() {
		return pautaRepository.findAll();
	}
}
