package br.com.pauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pauta.entity.Pauta;
import br.com.pauta.entity.Sessao;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.repository.SessaoRepository;

@Service
public class SessaoService {

	@Autowired
	private SessaoRepository sessaoRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private VotoService votoService;

	private Integer TEMPO_EM_MINUTOS_PADRAO = 1;

	public Sessao cadastrarSessao(Sessao sessao) throws Exception {
		sessao.setPauta(carregarPauta(sessao.getIdPauta()));
		sessao.setDataFim(calcularDataFinal(sessao.getTempoEmMinutos()));
		return sessaoRepository.save(sessao);
	}

	public Optional<Sessao> carregarSessao(Integer id) {
		return sessaoRepository.findById(id);
	}

	public List<Sessao> listarSessoes() {
		return sessaoRepository.findAll();
	}

	public Sessao listarVotosDaSessao(Integer idSessao) {
		Optional<Sessao> sessaoOptional = carregarSessao(idSessao);
		if (sessaoOptional.isPresent()) {
			Sessao sessao = sessaoOptional.get();
			sessao.setVotos(votoService.listarVotosPorSessao(idSessao));
			return sessao;
		} else {
			throw new ResourceNotFoundException("Sessão não encontrada.");			
		}
	}
	
	private LocalDateTime calcularDataFinal(Integer tempoEmMinutosInformado) {
		Integer tempoEmMinutos = Optional.ofNullable(tempoEmMinutosInformado).orElse(TEMPO_EM_MINUTOS_PADRAO);
		return LocalDateTime.now().plusMinutes(tempoEmMinutos);
	}
	
	private Pauta carregarPauta(Integer idPauta) {
		Optional<Pauta> pauta = pautaService.carregarPauta(idPauta);
		if (!pauta.isPresent()) {
			throw new ResourceNotFoundException("Pauta não encontrada.");
		}
		return pauta.get();
	}

}
