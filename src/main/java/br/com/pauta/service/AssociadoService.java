package br.com.pauta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pauta.entity.Associado;
import br.com.pauta.exception.ResourceNotFoundException;
import br.com.pauta.repository.AssociadoRepository;

@Service
public class AssociadoService {

	private AssociadoRepository associadoRepository;

	private AssociadoService(AssociadoRepository associadoRepository) {
		super();
		this.associadoRepository = associadoRepository;
	}

	public Associado cadastrarAssociado(Associado associado) {
		return associadoRepository.save(associado);
	}

	public Associado carregarAssociado(String cpf) {
		return associadoRepository.findByCpf(cpf).orElseThrow(() -> createExceptionResourceNotFound());
	}

	private ResourceNotFoundException createExceptionResourceNotFound() {
		return new ResourceNotFoundException("Associado não encontrado.");
	}

	public Associado carregarAssociado(Integer id) {
		return associadoRepository.findById(id).orElseThrow(() -> createExceptionResourceNotFound());
	}

	public List<Associado> listarAssociados() {
		return associadoRepository.findAll();
	}
}
