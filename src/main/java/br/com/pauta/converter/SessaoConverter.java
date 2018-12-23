package br.com.pauta.converter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pauta.config.exception.ResourceNotFoundException;
import br.com.pauta.dto.SessaoInput;
import br.com.pauta.dto.SessaoOutput;
import br.com.pauta.entity.Sessao;

@Component
public class SessaoConverter {

	@Autowired
	private ModelMapper conversor;
	
	@Autowired
	private PautaConverter pautaConverter;
	
    static final int SECONDS_PER_MINUTE = 60;

	public List<SessaoOutput> toArray(List<Sessao> listarSessoes) {
		return listarSessoes.stream().map(entity -> conversor.map(entity, SessaoOutput.class))
				.collect(Collectors.toList());
	}

	public Sessao toEntity(SessaoInput cadastrarSessaoInput) {
		 Sessao sessao = conversor.map(cadastrarSessaoInput, Sessao.class);
		 return sessao;
	}

	public SessaoOutput toOutput(Optional<Sessao> sessaoOptional) {
		if (sessaoOptional.isPresent()) {
			Sessao sessao = sessaoOptional.get();
			SessaoOutput output = conversor.map(sessao, SessaoOutput.class);
			output.setPauta(pautaConverter.toOutput(sessao.getPauta()));
			output.setDuracaoEmMinutos(diferencaTempoEmMinutosEntreDuasDatas(sessao.getDataInicio(), sessao.getDataFim()));
		}
		throw new ResourceNotFoundException("Sessão referente ao identificador informado não existe.");
	}

	public SessaoOutput toOutput(Sessao sessao) {
		return conversor.map(sessao, SessaoOutput.class);
	}
	
	public Long diferencaTempoEmMinutosEntreDuasDatas(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        Duration duration = Duration.between(dataInicial, dataFinal);

        Long diferencaEmSegundos = duration.getSeconds();
        return (diferencaEmSegundos * SECONDS_PER_MINUTE);
    }
}
