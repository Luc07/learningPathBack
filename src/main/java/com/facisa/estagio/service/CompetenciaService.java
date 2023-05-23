package com.facisa.estagio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facisa.estagio.model.Competencia;
import com.facisa.estagio.model.Usuario;
import com.facisa.estagio.repository.CompetenciaRepository;
import com.facisa.estagio.repository.UsuarioRepository;

@Service
public class CompetenciaService {
	@Autowired
	private CompetenciaRepository competenciaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Competencia criarCompetencia(Competencia competencia) {
		return competenciaRepository.save(competencia);
	}
	
	public List<Competencia> listarCompetencias(){
		return competenciaRepository.findAll();
	}

	public Optional<Competencia> listarCompetenciaPeloId(int id) {
		return competenciaRepository.findById(id);
	}

	public Object atualizarCompetencia(int id, Competencia competencia) {
		Optional<Competencia> comp = competenciaRepository.findById(id);
		if(!comp.isPresent()) {
			return null;
		}
		comp.get().setData(competencia.getData() == null ? comp.get().getData(): competencia.getData());
		comp.get().setSituacao(competencia.isSituacao() == false ? comp.get().isSituacao(): competencia.isSituacao());
		comp.get().setLink(competencia.getLink() == null ? comp.get().getLink(): competencia.getLink());
		comp.get().setCompetencia(competencia.getCompetencia() == null ? comp.get().getCompetencia(): competencia.getCompetencia());
		return competenciaRepository.save(comp.get());
	}

	public void deletarPeloId(int id) {
		Optional<Competencia> comp = listarCompetenciaPeloId(id);
		for (Usuario usuario : new ArrayList<Usuario>(comp.get().getUsuarios())) {
			usuario.removerCompetencia(comp.get());
		}
		competenciaRepository.deleteById(id);
	}

	public boolean existePeloId(int competenciaId) {
		return competenciaRepository.existsById(competenciaId);
	}

	public List<Usuario> listarUsuariosPelaCompetenciaId(int competenciaId) {
		return usuarioRepository.findUsuariosByCompetenciasId(competenciaId);
	}
}
