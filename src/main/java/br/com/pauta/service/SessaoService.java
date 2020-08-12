package br.com.pauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.repository.SessaoRepository;

@Service
public class SessaoService {

	private final Integer TEMPO_EM_MINUTOS_PADRAO = 1;

	private final SessaoRepository sessaoRepository;
	private final PautaService pautaService;
	private final VotoService votoService;

	private SessaoService(SessaoRepository sessaoRepository, PautaService pautaService, VotoService votoService) {
		super();
		this.sessaoRepository = sessaoRepository;
		this.pautaService = pautaService;
		this.votoService = votoService;
	}

	public Sessao cadastrarSessao(Sessao sessao) throws Exception {
		sessao.setPauta(carregarPauta(sessao.getIdPauta()));
		sessao.setDataFim(calcularDataFinal(sessao.getTempoEmMinutos()));
		return sessaoRepository.save(sessao);
	}

	public Sessao carregarSessao(Integer id) {
		return sessaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada."));
	}

	public List<Sessao> listarSessoes() {
		return sessaoRepository.findAll();
	}

	public Sessao listarVotosDaSessao(Integer idSessao) {
		Sessao sessao = carregarSessao(idSessao);
		sessao.setVotos(votoService.listarVotosPorSessao(idSessao));
		return sessao;
	}

	private LocalDateTime calcularDataFinal(Integer tempoEmMinutosInformado) {
		return LocalDateTime.now().plusMinutes(tempoEmMinutos(tempoEmMinutosInformado));
	}

	private Integer tempoEmMinutos(Integer tempoEmMinutosInformado) {
		return Optional.ofNullable(tempoEmMinutosInformado).orElse(TEMPO_EM_MINUTOS_PADRAO);
	}

	private Pauta carregarPauta(Integer idPauta) {
		return pautaService.carregarPauta(idPauta);
	}
}
