package com.facisa.estagio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facisa.estagio.model.Competencia;
import com.facisa.estagio.model.Usuario;
import com.facisa.estagio.repository.CompetenciaRepository;
import com.facisa.estagio.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CompetenciaRepository competenciaRepository;
	
	public Usuario criarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> listarUsuarioPeloId(int id) {
		Optional<Usuario> usr = usuarioRepository.findById(id);
		return usr;
	}

	public void deletarPeloId(int id) {
		usuarioRepository.deleteById(id);
	}

	public Object atualizarUsuario(int id, Usuario usuario) {
		Optional<Usuario> usr = usuarioRepository.findById(id);
		if(!usr.isPresent()) {
			return null;
		}
		usr.get().setMatricula(usuario.getMatricula() == null ? usr.get().getMatricula(): usuario.getMatricula());
		usr.get().setSituacao(usuario.isSituacao() == false ? usr.get().isSituacao(): usuario.isSituacao());
		usr.get().setPerfil(usuario.getPerfil() == null ? usr.get().getPerfil(): usuario.getPerfil());
		return usuarioRepository.save(usr.get());
	}

	public boolean existePeloId(int usuarioId) {
		return usuarioRepository.existsById(usuarioId);
	}

	public List<Competencia> listarCompetenciasPeloUsuarioId(int usuarioId) {
		return competenciaRepository.findCompetenciasByUsuariosId(usuarioId);
	}
}
