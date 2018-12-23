package br.com.pauta.dto;

public class VotoSimplificadoOutput {

	private AssociadoSimplificadoOutput associado;

	private String voto;

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public AssociadoSimplificadoOutput getAssociado() {
		return associado;
	}

	public void setAssociado(AssociadoSimplificadoOutput associado) {
		this.associado = associado;
	}

}
