package br.com.pauta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pauta.entity.Sessao;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.repository.SessaoRepository;

@Service
public class SessaoService {

	private final SessaoRepository sessaoRepository;

	private SessaoService(SessaoRepository sessaoRepository) {
		super();
		this.sessaoRepository = sessaoRepository;
	}

	public Sessao cadastrarSessao(Sessao sessao) throws Exception {
		return sessaoRepository.save(sessao);
	}

	public Sessao carregarSessao(Integer id) {
		return sessaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada."));
	}

	public List<Sessao> listarSessoes() {
		return sessaoRepository.findAll();
	}
}
