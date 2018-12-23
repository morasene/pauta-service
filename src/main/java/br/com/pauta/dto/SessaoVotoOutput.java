package br.com.pauta.dto;

import java.util.List;

public class SessaoVotoOutput {

	private SessaoOutput sessao;

	private List<VotoSimplificadoOutput> votos;

	public SessaoOutput getSessao() {
		return sessao;
	}

	public void setSessao(SessaoOutput sessao) {
		this.sessao = sessao;
	}

	public List<VotoSimplificadoOutput> getVotos() {
		return votos;
	}

	public void setVotos(List<VotoSimplificadoOutput> votos) {
		this.votos = votos;
	}

}
