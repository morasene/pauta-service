package br.com.pauta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Pauta {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Id
	private Integer id;

	@NotBlank(message = "Tema é obrigatório")
	@Column(unique = true)
	private String tema;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

}
