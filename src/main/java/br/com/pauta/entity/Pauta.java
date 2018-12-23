package br.com.pauta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

@Entity
public class Pauta {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Id
	private Integer idPauta;

	@NotBlank(message = "Tema é obrigatório")
	@Column(unique = true)
	private String tema;

	@Column
	private Integer quantidadeVotosSim;

	@Column
	private Integer quantidadeVotosNao;

	public Integer getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Integer idPauta) {
		this.idPauta = idPauta;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Integer getQuantidadeVotosSim() {
		return quantidadeVotosSim;
	}

	public void setQuantidadeVotosSim(Integer quantidadeVotosSim) {
		this.quantidadeVotosSim = quantidadeVotosSim;
	}

	public Integer getQuantidadeVotosNao() {
		return quantidadeVotosNao;
	}

	public void setQuantidadeVotosNao(Integer quantidadeVotosNao) {
		this.quantidadeVotosNao = quantidadeVotosNao;
	}

	@PrePersist
	public void prePersist() {
		quantidadeVotosSim = 0;
		quantidadeVotosNao = 0;
	}
}
