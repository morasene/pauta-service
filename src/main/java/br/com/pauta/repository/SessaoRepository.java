package br.com.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pauta.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {

}
