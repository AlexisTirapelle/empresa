package com.api.empresa.dtos;

import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {

	@NotBlank
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
