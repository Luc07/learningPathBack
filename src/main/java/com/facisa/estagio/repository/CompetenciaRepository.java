package com.facisa.estagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facisa.estagio.model.Competencia;

public interface CompetenciaRepository extends JpaRepository<Competencia, Integer>{
	List<Competencia> findCompetenciasByUsuariosId(int usuarioId);
}
