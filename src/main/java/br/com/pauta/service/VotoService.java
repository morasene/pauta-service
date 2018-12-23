package br.com.pauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pauta.config.exception.BusinessException;
import br.com.pauta.config.exception.ResourceNotFoundException;
import br.com.pauta.entity.Associado;
import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;
import br.com.pauta.enumeration.VotoEnum;
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
		verificarSeAssociadoJaVotou(voto);
		voto.setAssociado(carregarAssociado(voto.getIdAssociado()));
		Sessao sessao = carregarObjetoSessaoParaVoto(voto.getIdSessao());
		verificarValidadeDaSessao(sessao.getDataFim());
		atualizarQuantidadeDeVotosNaPauta(sessao.getPauta(), voto.getVoto());
		voto.setSessao(sessao);
		return votoRepository.save(voto);
	}

	private void verificarSeAssociadoJaVotou(Voto votoNovo) throws Exception {
		List<Voto> listarVotosPorSessao = listarVotosPorSessao(votoNovo.getIdSessao());
		if (listarVotosPorSessao != null) {
			Optional<Voto> associadoVotou = listarVotosPorSessao.stream().filter(voto -> votoNovo.getIdAssociado() == voto.getAssociado().getIdAssociado()).findFirst();
			if (associadoVotou.isPresent()) {
				throw new BusinessException("Associado ja efetuou o voto para esta pauta.");
			}
		}
	}

	private void atualizarQuantidadeDeVotosNaPauta(Pauta pauta, VotoEnum voto) {
		if (VotoEnum.SIM.equals(voto)) {
			pauta.setQuantidadeVotosSim(pauta.getQuantidadeVotosSim() + 1);
		} else {
			pauta.setQuantidadeVotosNao(pauta.getQuantidadeVotosNao() + 1);			
		}
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

	private Sessao carregarObjetoSessaoParaVoto(Integer idSessao) throws Exception {
		Optional<Sessao> sessaoOptional = sessaoService.carregarSessao(idSessao);
		if (!sessaoOptional.isPresent()) {
			throw new ResourceNotFoundException("Sessão não encontrada.");
		}
		return sessaoOptional.get();
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
