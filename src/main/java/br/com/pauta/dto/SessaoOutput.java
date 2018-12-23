package br.com.pauta.dto;

import java.time.LocalDateTime;

public class SessaoOutput {

	private Integer idSessao;

	private LocalDateTime dataInicio;

	private LocalDateTime dataFim;

	private PautaOutput pauta;

	private Long duracaoEmMinutos;

	public Integer getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(Integer idSessao) {
		this.idSessao = idSessao;
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

	public PautaOutput getPauta() {
		return pauta;
	}

	public void setPauta(PautaOutput pauta) {
		this.pauta = pauta;
	}

	public Long getDuracaoEmMinutos() {
		return duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(Long duracaoEmMinutos) {
		this.duracaoEmMinutos = duracaoEmMinutos;
	}
}
