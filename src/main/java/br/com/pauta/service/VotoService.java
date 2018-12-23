package br.com.pauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pauta.config.exception.ResourceNotFoundException;
import br.com.pauta.entity.Associado;
import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;
import br.com.pauta.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private SessaoService sessaoService;

	public Voto cadastrarVoto(Voto voto) throws Exception {
		voto.setAssociado(carregarAssociado(voto.getIdAssociado()));
		voto.setSessao(carregarSessao(voto.getIdSessao()));
		return votoRepository.save(voto);
	}

	public Voto carregarVoto(Integer id) {
		return votoRepository.findById(id).orElse(null);
	}

	public List<Voto> listarVotos() {
		return votoRepository.findAll();
	}
	
	public List<Voto> listarVotosPorSessao(Integer idSessao) {
		return votoRepository.findBySessaoIdSessao(idSessao);
	}

	private Sessao carregarSessao(Integer idSessao) throws Exception {
		Optional<Sessao> sessaoOptional = sessaoService.carregarSessao(idSessao);
		verificarExistenciaDaSessao(sessaoOptional.isPresent());
		Sessao sessao = sessaoOptional.get();
		verificarValidadeDaSessao(sessao.getDataFim());
		return sessao;
	}

	private void verificarExistenciaDaSessao(Boolean estaPresente) {
		if (!estaPresente) {
			throw new ResourceNotFoundException("Sessão não encontrada.");
		}
	}

	private void verificarValidadeDaSessao(LocalDateTime dataFim) throws Exception {
		if (dataFim.isBefore(LocalDateTime.now())) {
			throw new Exception("Sessão expirada.");
		}
	}

	private Associado carregarAssociado(Integer idAssociado) {
		Optional<Associado> associado = associadoService.carregarAssociado(idAssociado);
		if (!associado.isPresent()) {
			throw new ResourceNotFoundException("Associado não encontrado.");
		}
		return associado.get();
	}
}
