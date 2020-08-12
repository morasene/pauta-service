package br.com.pauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pauta.entity.Associado;
import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;
import br.com.pauta.enumeration.VotoEnum;
import br.com.pauta.exception.BusinessException;
import br.com.pauta.repository.VotoRepository;

@Service
public class VotoService {

	private final VotoRepository votoRepository;
	private final AssociadoService associadoService;
	private final SessaoService sessaoService;

	private VotoService(VotoRepository votoRepository, AssociadoService associadoService, SessaoService sessaoService) {
		super();
		this.votoRepository = votoRepository;
		this.associadoService = associadoService;
		this.sessaoService = sessaoService;
	}

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
		if (!listarVotosPorSessao.isEmpty()) {
			if (listarVotosPorSessao.stream().anyMatch(voto -> votoNovo.getIdAssociado() == voto.getAssociado().getIdAssociado())) {
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

	public Optional<Voto> carregarVoto(Integer id) {
		return votoRepository.findById(id);
	}

	public List<Voto> listarVotos() {
		return votoRepository.findAll();
	}

	public List<Voto> listarVotosPorSessao(Integer idSessao) {
		return votoRepository.findBySessaoIdSessao(idSessao);
	}

	private Sessao carregarObjetoSessaoParaVoto(Integer idSessao) throws Exception {
		return sessaoService.carregarSessao(idSessao);
	}

	private void verificarValidadeDaSessao(LocalDateTime dataFim) throws Exception {
		if (dataFim.isBefore(LocalDateTime.now())) {
			throw new BusinessException("Sessão expirada.");
		}
	}

	private Associado carregarAssociado(Integer idAssociado) {
		return associadoService.carregarAssociado(idAssociado);
	}
}
