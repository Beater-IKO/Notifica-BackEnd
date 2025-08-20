package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.repositories.CursoRepository;

import java.util.List;

// Serviço para cursos
@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    // Salvar curso
    public Curso save(Curso curso){
        return cursoRepository.save(curso);
    }

    // Listar cursos
    public List<Curso> findAll(){
        return cursoRepository.findAll();
    }

    // Buscar por ID
    public Curso findById(Integer id){
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    // Atualizar curso
    public Curso update(Integer id, Curso curso){
        Curso existingCurso = findById(id);

        if(curso.getNome() != null && !curso.getNome().isBlank()){
            existingCurso.setNome(curso.getNome());
        }

        if(curso.getDuracao() != 0){
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