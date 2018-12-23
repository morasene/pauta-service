package br.com.pauta.dto;

public class SessaoSimplificadaOutput {

	private Integer idSessao;

	private String dataInicio;

	private String dataFim;

	private PautaSimplificadaOutput pauta;

	private Long duracaoEmMinutos;

	public Integer getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(Integer idSessao) {
		this.idSessao = idSessao;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public PautaSimplificadaOutput getPauta() {
		return pauta;
	}

	public void setPauta(PautaSimplificadaOutput pauta) {
		this.pauta = pauta;
	}

	public Long getDuracaoEmMinutos() {
		return duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(Long duracaoEmMinutos) {
		this.duracaoEmMinutos = duracaoEmMinutos;
	}
}
