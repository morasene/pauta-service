package br.com.pauta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pauta.entity.Voto;
import br.com.pauta.repository.VotoRepository;

@Service
public class VotoService {

	private final VotoRepository votoRepository;

	private VotoService(VotoRepository votoRepository) {
		super();
		this.votoRepository = votoRepository;
	}

	public Voto cadastrarVoto(Voto voto) {
		return votoRepository.save(voto);
	}

	public Optional<Voto> carregarVoto(Integer id) {
		return votoRepository.findById(id);
	}

	public List<Voto> listarVotos() {
		return votoRepository.findAll();
	}

	public List<Voto> listarVotosPorSessao(Integer idSessao) {
		return votoRepository.findBySessaoIdSessao(idSessao);
	}
}
