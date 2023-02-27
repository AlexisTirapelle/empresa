package com.api.empresa.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<Page<UsuarioModel>> getAllUsuarios(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneUsuario(@PathVariable(value = "id") UUID id){
		Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
		if(!usuarioModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(usuarioModelOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "id") UUID id){
		Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
		if(!usuarioModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		}
		usuarioService.delete(usuarioModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Usuário excluido com sucesso.");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateUsuario(@PathVariable(value = "id") UUID id, @RequestBody @Valid UsuarioDTO usuarioDTO){
		Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
		if(!usuarioModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		}
		var usuarioModel = new UsuarioModel();
		BeanUtils.copyProperties(usuarioDTO, usuarioModel);
		usuarioModel.setId(usuarioModelOptional.get().getId());
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(usuarioModel));
	}
	
}
