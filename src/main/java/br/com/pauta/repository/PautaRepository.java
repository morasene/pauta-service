package br.com.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pauta.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

}
