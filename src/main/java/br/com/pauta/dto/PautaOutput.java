package br.com.pauta.dto;

public class PautaOutput {

	private Integer idPauta;
	private String tema;
	private Integer quantidadeVotosSim;
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

}
