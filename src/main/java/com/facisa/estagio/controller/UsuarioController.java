package com.facisa.estagio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.facisa.estagio.model.Usuario;
import com.facisa.estagio.service.UsuarioService;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/usuario")
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		return ResponseEntity.status(HttpStatus.OK).body(service.listarUsuarios());
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Object> listarUsuarioPeloId(@PathVariable int id){
		Optional<Usuario> usr = service.listarUsuarioPeloId(id);
		if(!usr.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		}
		return ResponseEntity.status(HttpStatus.OK).body(usr);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarUsuario(usuario));
	}
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<Object> atualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario){
		Object resposta = service.atualizarUsuario(id, usuario);
		
		if(resposta == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
	}
	
	@DeleteMapping("/usuario/{id}")
	 public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable int id) {
	    service.deletarPeloId(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
}
