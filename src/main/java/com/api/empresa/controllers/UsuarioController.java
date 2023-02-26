package com.api.empresa.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.empresa.dtos.UsuarioDTO;
import com.api.empresa.models.UsuarioModel;
import com.api.empresa.services.UsuarioService;

import jakarta.validation.Valid;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

	
	final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveUsuario (@RequestBody @Valid UsuarioDTO usuarioDTO){
		
        if(usuarioService.existsByNome(usuarioDTO.getNome())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Nome de usuário já está em uso!");
        }
		
		var usuarioModel = new UsuarioModel();
		BeanUtils.copyProperties(usuarioDTO, usuarioModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioModel));
	}

	@GetMapping
	public ResponseEntity<List<UsuarioModel>> getAllUsuarios(){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
		
	}
	
}
