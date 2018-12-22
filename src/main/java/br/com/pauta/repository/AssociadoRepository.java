package br.com.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pauta.entity.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Integer> {

	Associado findByCpf(String cpf);
}
