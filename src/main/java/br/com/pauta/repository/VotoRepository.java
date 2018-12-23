package br.com.pauta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pauta.entity.Sessao;
import br.com.pauta.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {

	List<Voto> findBySessao(Sessao sessao);
	
	List<Voto> findBySessaoIdSessao(Integer idSessao);
}
