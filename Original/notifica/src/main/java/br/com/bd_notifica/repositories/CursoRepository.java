package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Curso;

// Repositório para cursos
@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    // Busca curso por nome
    List<Curso> findByNomeIgnoreCase(String nome);
    // Cursos com duração maior que X horas
    List<Curso> findByDuracaoGreaterThan(Integer duracao);
}
