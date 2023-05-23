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

import com.facisa.estagio.model.Competencia;
import com.facisa.estagio.model.Usuario;
import com.facisa.estagio.service.CompetenciaService;
import com.facisa.estagio.service.UsuarioService;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CompetenciaController {
	@Autowired
	private CompetenciaService competenciaService;
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/competencia")
	public ResponseEntity<List<Competencia>> listarCompetencias(){
		return ResponseEntity.status(HttpStatus.OK).body(competenciaService.listarCompetencias());
	}
	
	@GetMapping("/competencia/{id}")
	public ResponseEntity<Object> listarCompetenciaPeloId(@PathVariable int id){
		Optional<Competencia> comp = competenciaService.listarCompetenciaPeloId(id);
		if(!comp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competencia não encontrada");
		}
		return ResponseEntity.status(HttpStatus.OK).body(competenciaService.listarCompetenciaPeloId(id));
	}
	
	@GetMapping("/usuario/{usuarioId}/competencias")
	public ResponseEntity<List<Competencia>> listarTodasCompetenciasPeloUsuarioId(@PathVariable int usuarioId){
		if(!usuarioService.existePeloId(usuarioId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarCompetenciasPeloUsuarioId(usuarioId));
	}
	
	@GetMapping("/competencia/{competenciaId}/usuarios")
	public ResponseEntity<List<Usuario>> listarTodosUsuariosPelaCompetenciaId(@PathVariable int competenciaId){
		if(!competenciaService.existePeloId(competenciaId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.OK).body(competenciaService.listarUsuariosPelaCompetenciaId(competenciaId));
	}
	
	@PostMapping("/competencia")
	public ResponseEntity<Competencia> adicionarCompetencia(@RequestBody Competencia competencia){
		return ResponseEntity.status(HttpStatus.CREATED).body(competenciaService.criarCompetencia(competencia));
	}
	
	@PutMapping("/usuario/{usuarioId}/competencia/{competenciaId}")
	public ResponseEntity<Object> adicionarCompetenciaAoUsuario(@PathVariable int usuarioId, @PathVariable int competenciaId){
		Optional<Usuario> usuarioOptional = usuarioService.listarUsuarioPeloId(usuarioId);
		if(!usuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
		}
		Usuario usr = usuarioOptional.get();
		Optional<Competencia> competenciaOptional = competenciaService.listarCompetenciaPeloId(competenciaId);
		if(!competenciaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competencia não encontrada");
		}
		Competencia comp = competenciaOptional.get();
		usr.addCompetencia(comp);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.atualizarUsuario(usuarioId, usr));
	}
	
	@PutMapping("/competencia/{id}")
	public ResponseEntity<Object> atualizarCompetencia(@PathVariable int id, @RequestBody Competencia competencia){
		Object resposta = competenciaService.atualizarCompetencia(id, competencia);
		
		if(resposta == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competencia não encontrada");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
	}
	
	@DeleteMapping("/competencia/{id}")
	  public ResponseEntity<HttpStatus> removerCompetencia(@PathVariable int id) {
	    competenciaService.deletarPeloId(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/usuario/{usuarioId}/competencia/{competenciaId}")
	public ResponseEntity<HttpStatus> removerCompetenciaDoUsuario(@PathVariable int usuarioId, @PathVariable int competenciaId){
		Optional<Usuario> usr = usuarioService.listarUsuarioPeloId(usuarioId);
		if(!usr.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Competencia> comp = competenciaService.listarCompetenciaPeloId(competenciaId);
		usr.get().removerCompetencia(comp.get());
		usuarioService.atualizarUsuario(usuarioId, usr.get());
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
