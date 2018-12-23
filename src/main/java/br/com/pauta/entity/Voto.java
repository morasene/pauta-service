package br.com.pauta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.com.pauta.VotoEnum;

@Entity
public class Voto {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVoto;

	@OneToOne
	private Associado associado;

	@ManyToOne
	private Sessao sessao;

	@Transient
	private Integer idAssociado;

	@Transient
	private Integer idSessao;

	@Enumerated(EnumType.STRING)
	private VotoEnum voto;

	public Integer getIdVoto() {
		return idVoto;
	}

	public void setIdVoto(Integer idVoto) {
		this.idVoto = idVoto;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public VotoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoEnum voto) {
		this.voto = voto;
	}

	public Integer getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Integer idAssociado) {
		this.idAssociado = idAssociado;
	}

	public Integer getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(Integer idSessao) {
		this.idSessao = idSessao;
	}

	@Override
	public String toString() {
		return "Voto [idVoto=" + idVoto + ", associado=" + associado + ", idAssociado=" + idAssociado + ", idSessao="
				+ idSessao + ", voto=" + voto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAssociado == null) ? 0 : idAssociado.hashCode());
		result = prime * result + ((idSessao == null) ? 0 : idSessao.hashCode());
		result = prime * result + ((idVoto == null) ? 0 : idVoto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voto other = (Voto) obj;
		if (idAssociado == null) {
			if (other.idAssociado != null)
				return false;
		} else if (!idAssociado.equals(other.idAssociado))
			return false;
		if (idSessao == null) {
			if (other.idSessao != null)
				return false;
		} else if (!idSessao.equals(other.idSessao))
			return false;
		if (idVoto == null) {
			if (other.idVoto != null)
				return false;
		} else if (!idVoto.equals(other.idVoto))
			return false;
		return true;
	}

}
