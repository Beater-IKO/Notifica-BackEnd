package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.repositories.CursoRepository;

import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.config.GenericExceptions.AlreadyExists;
import br.com.bd_notifica.config.GenericExceptions.NotFound;

import java.util.List;

// Serviço para cursos
@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Salvar curso
    public Curso save(Curso curso) {
        if (curso == null || curso.getNome() == null || curso.getNome().isBlank()) {
            throw new ValidationException("Curso e nome não podem ser nulos ou vazios");
        }

        // Validação de duplicação
        if (!cursoRepository.findByNomeIgnoreCase(curso.getNome()).isEmpty()) {
            throw new AlreadyExists("Já existe um curso com o nome '" + curso.getNome() + "'");
        }

        return cursoRepository.save(curso);
    }

    // Listar cursos
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    // Buscar por ID
    public Curso findById(Integer id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new NotFound("Curso com ID " + id + " não encontrado"));
    }

    // Atualizar curso
    public Curso update(Integer id, Curso curso) {
        if (curso == null) {
            throw new ValidationException("Dados do curso não podem ser nulos");
        }

        Curso existingCurso = findById(id);

        if (curso.getNome() != null && !curso.getNome().isBlank()) {
            // Verificar duplicação apenas se o nome for diferente
            if (!curso.getNome().equalsIgnoreCase(existingCurso.getNome()) &&
                    !cursoRepository.findByNomeIgnoreCase(curso.getNome()).isEmpty()) {
                throw new AlreadyExists("Já existe um curso com o nome '" + curso.getNome() + "'");
            }
            existingCurso.setNome(curso.getNome());
        }

        if (curso.getDuracao() != 0) {
            existingCurso.setDuracao(curso.getDuracao());
        }

        return cursoRepository.save(existingCurso);
    }

    // Excluir curso
    public void delete(Integer id) {
        Curso cursoToDelete = findById(id);
        cursoRepository.delete(cursoToDelete);
    }
}