package br.com.pauta.dto;

public class VotoOutput {

	private Integer idVoto;

	private SessaoOutput sessao;

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

	public SessaoOutput getSessao() {
		return sessao;
	}

	public void setSessao(SessaoOutput sessao) {
		this.sessao = sessao;
	}

	public AssociadoOutput getAssociado() {
		return associado;
	}

	public void setAssociado(AssociadoOutput associado) {
		this.associado = associado;
	}

}
