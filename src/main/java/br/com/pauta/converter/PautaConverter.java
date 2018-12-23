package br.com.pauta.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.pauta.config.exception.ResourceNotFoundException;
import br.com.pauta.dto.PautaInput;
import br.com.pauta.dto.PautaOutput;
import br.com.pauta.entity.Pauta;

@Component
public class PautaConverter {

	private ModelMapper conversor = new ModelMapper();

	public List<PautaOutput> toArray(List<Pauta> listarPautas) {
		return listarPautas.stream().map(entity -> conversor.map(entity, PautaOutput.class))
				.collect(Collectors.toList());
	}

	public Pauta toEntity(PautaInput cadastrarPautaInput) {
		return conversor.map(cadastrarPautaInput, Pauta.class);
	}

	public PautaOutput toOutput(Optional<Pauta> pauta) {
		if (pauta.isPresent()) {			
			return conversor.map(pauta.get(), PautaOutput.class);
		}
		throw new ResourceNotFoundException("Pauta referente ao identificador informado não existe.");
	}

	public PautaOutput toOutput(Pauta pauta) {
		return conversor.map(pauta, PautaOutput.class);
	}
}
