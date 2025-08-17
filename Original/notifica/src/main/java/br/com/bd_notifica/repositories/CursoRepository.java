package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findByNomeIgnoreCase(String nome);
    List<Curso> findByDuracaoGreaterThan(Integer duracao);
}
