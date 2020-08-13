package br.com.pauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pauta.config.CacheConfig;
import br.com.pauta.entity.Associado;
import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;
import br.com.pauta.enumeration.VotoEnum;
import br.com.pauta.exception.BusinessException;
import br.com.pauta.vendor.client.UserClient;
import br.com.pauta.vendor.dto.StatusVote;
import br.com.pauta.vendor.dto.UserDTO;

@Service
public class VotoBusiness {

	private final VotoService votoService;
	private final AssociadoService associadoService;
	private final SessaoService sessaoService;
	private final CacheConfig cacheConfig;
	private final UserClient userClient;

	private VotoBusiness(VotoService votoService, AssociadoService associadoService, SessaoService sessaoService, CacheConfig cacheConfig,
			UserClient userClient) {
		super();
		this.votoService = votoService;
		this.associadoService = associadoService;
		this.sessaoService = sessaoService;
		this.cacheConfig = cacheConfig;
		this.userClient = userClient;
	}

	public Voto cadastrarVoto(Voto voto) {
		verificarSeAssociadoJaVotou(voto);
		voto.setAssociado(carregarAssociado(voto.getIdAssociado()));
		Sessao sessao = carregarObjetoSessaoParaVoto(voto.getIdSessao());
		verificarValidadeDaSessao(sessao.getDataFim());
		atualizarQuantidadeDeVotosNaPauta(sessao.getPauta(), voto.getVoto());
		voto.setSessao(sessao);
		return votoService.cadastrarVoto(voto);
	}

	private void verificarSeAssociadoJaVotou(Voto votoNovo) {
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
		return votoService.carregarVoto(id);
	}

	public List<Voto> listarVotos() {
		return votoService.listarVotos();
	}

	public List<Voto> listarVotosPorSessao(Integer idSessao) {
		return votoService.listarVotosPorSessao(idSessao);
	}

	private Sessao carregarObjetoSessaoParaVoto(Integer idSessao) {
		return sessaoService.carregarSessao(idSessao);
	}

	private void verificarValidadeDaSessao(LocalDateTime dataFim) {
		if (verificarSeDataMaiorQueHoje(dataFim)) {
			throw new BusinessException("Sessão expirada.");
		}
	}

	private boolean verificarSeDataMaiorQueHoje(LocalDateTime dataFim) {
		return dataFim.isBefore(LocalDateTime.now());
	}

	private Associado carregarAssociado(Integer idAssociado) {
		Associado associado = carregarAssociadoRepositorio(idAssociado);
		UserDTO user = userClient.findByCpf(associado.getCpf());
		if (StatusVote.UNABLE_TO_VOTE == user.getStatus()) {
			throw new BusinessException("Associado não está apto a votar.");
		}
		return associado;
	}

	private Associado carregarAssociadoRepositorio(Integer idAssociado) {
		return cacheConfig.associadoCache().get(idAssociado, key -> {
			return associadoService.carregarAssociado(idAssociado);
		});
	}
}
