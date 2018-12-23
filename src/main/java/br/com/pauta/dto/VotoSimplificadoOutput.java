package br.com.pauta.dto;

public class VotoSimplificadoOutput {

	private Integer idVoto;

	private AssociadoOutput associado;

	private String voto;

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public Integer getIdVoto() {
		return idVoto;
	}

	public void setIdVoto(Integer idVoto) {
		this.idVoto = idVoto;
	}

	public AssociadoOutput getAssociado() {
		return associado;
	}

	public void setAssociado(AssociadoOutput associado) {
		this.associado = associado;
	}

}
