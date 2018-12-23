package br.com.pauta.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@Entity
public class Sessao {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(updatable = false)
	private LocalDateTime dataInicio;

	@Column
	private LocalDateTime dataFim;

	@OneToOne
	private Pauta pauta;

	@Transient
	private Integer idPauta;

	@Transient
	private Optional<Integer> tempoEmMinutos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public Integer getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Integer idPauta) {
		this.idPauta = idPauta;
	}

	public Optional<Integer> getTempoEmMinutos() {
		return tempoEmMinutos;
	}

	public void setTempoEmMinutos(Optional<Integer> tempoEmMinutos) {
		this.tempoEmMinutos = tempoEmMinutos;
	}

	@PrePersist
	public void prePersist() {
		dataInicio = LocalDateTime.now();
	}
}