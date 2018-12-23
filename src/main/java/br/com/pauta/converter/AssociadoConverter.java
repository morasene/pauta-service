package br.com.pauta.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pauta.dto.AssociadoInput;
import br.com.pauta.dto.AssociadoOutput;
import br.com.pauta.entity.Associado;

@Component
public class AssociadoConverter {

	@Autowired
	private ModelMapper conversor;

	public List<AssociadoOutput> toArray(List<Associado> listarAssociados) {
		return listarAssociados.stream().map(entity -> conversor.map(entity, AssociadoOutput.class))
				.collect(Collectors.toList());
	}

	public Associado toEntity(AssociadoInput cadastrarAssociadoInput) {
		return conversor.map(cadastrarAssociadoInput, Associado.class);
	}

	public AssociadoOutput toOutput(Associado associado) {
		return conversor.map(associado, AssociadoOutput.class);
	}
}
