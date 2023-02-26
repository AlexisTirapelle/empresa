package com.api.empresa.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.empresa.models.UsuarioModel;
import com.api.empresa.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	final UsuarioRepository usuarioRepository;
	
	public UsuarioService (UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public UsuarioModel save(UsuarioModel usuarioModel) {
		return usuarioRepository.save(usuarioModel);
	}
	
	public boolean existsByNome(String nome) {
		return usuarioRepository.existsByNome(nome);
	}

	public List<UsuarioModel> findAll() {
		return usuarioRepository.findAll();
	}
	
}
