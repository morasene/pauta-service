package br.com.pauta.dto;

import java.util.List;

public class SessaoVotoOutput {

	private SessaoSimplificadaOutput sessao;

	private List<VotoSimplificadoOutput> votos;

	public SessaoSimplificadaOutput getSessao() {
		return sessao;
	}

	public void setSessao(SessaoSimplificadaOutput sessao) {
		this.sessao = sessao;
	}

	public List<VotoSimplificadoOutput> getVotos() {
		return votos;
	}

	public void setVotos(List<VotoSimplificadoOutput> votos) {
		this.votos = votos;
	}

}
