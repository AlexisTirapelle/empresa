package com.api.empresa.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<UsuarioModel> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	public Optional<UsuarioModel> findById(UUID id) {
		return usuarioRepository.findById(id);
	}

	@Transactional
	public void delete(UsuarioModel usuarioModel) {
		usuarioRepository.delete(usuarioModel);
	}
	
}
