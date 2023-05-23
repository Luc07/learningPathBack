package com.facisa.estagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facisa.estagio.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	List<Usuario> findUsuariosByCompetenciasId(int competenciaId);
}
