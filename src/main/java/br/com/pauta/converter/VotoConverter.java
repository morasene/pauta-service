package br.com.pauta.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pauta.config.exception.ResourceNotFoundException;
import br.com.pauta.dto.VotoInput;
import br.com.pauta.dto.VotoOutput;
import br.com.pauta.dto.VotoSimplificadoOutput;
import br.com.pauta.entity.Voto;

@Component
public class VotoConverter {

	@Autowired
	private ModelMapper conversor;

	@Autowired
	private SessaoConverter sessaoConverter;

	@Autowired
	private AssociadoConverter associadoConverter;

	public List<VotoOutput> arrayEntitytoArrayDTOOutput(List<Voto> listarVotos) {
		return listarVotos.stream().map(entity -> conversor.map(entity, VotoOutput.class)).collect(Collectors.toList());
	}
	
	public List<VotoSimplificadoOutput> arrayEntitytoArrayDTOSimplificadoOutput(List<Voto> listarVotos) {
		return listarVotos.stream().map(entity -> conversor.map(entity, VotoSimplificadoOutput.class)).collect(Collectors.toList());
	}
	
	public Voto dtoInputToEntity(VotoInput cadastrarVotoInput) {
		Voto voto = new Voto();
		voto.setIdAssociado(cadastrarVotoInput.getIdAssociado());
		voto.setIdSessao(cadastrarVotoInput.getIdSessao());
		voto.setVoto(cadastrarVotoInput.getVoto());
		return voto;
		//return conversor.map(cadastrarVotoInput, Voto.class);
	}

	public VotoOutput entityToDTOOutput(Optional<Voto> votoOptional) {
		if (votoOptional.isPresent()) {
			Voto voto = votoOptional.get();
			VotoOutput output = conversor.map(voto, VotoOutput.class);
			output.setSessao(sessaoConverter.toOutput(voto.getSessao()));
			output.setAssociado(associadoConverter.toOutput(voto.getAssociado()));
			return output;
		}
		throw new ResourceNotFoundException("Voto referente ao identificador informado não existe.");
	}

	public VotoOutput entityToDTOOutput(Voto voto) {
		return conversor.map(voto, VotoOutput.class);
	}
}
