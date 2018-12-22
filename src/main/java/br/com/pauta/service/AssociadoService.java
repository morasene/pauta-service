package br.com.pauta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pauta.entity.Associado;
import br.com.pauta.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository associadoRepository;

	public Associado cadastrarAssociado(Associado associado) {
		return associadoRepository.save(associado);
	}

	public Associado carregarAssociado(String cpf) {
		return associadoRepository.findByCpf(cpf);
	}

	public List<Associado> listarAssociados() {
		return associadoRepository.findAll();
	}
}
