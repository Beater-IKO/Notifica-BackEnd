package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.repositories.CursoRepository;

import java.util.List;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    public Curso save(Curso curso){
        return cursoRepository.save(curso);
    }

    public List<Curso> findAll(){
        return cursoRepository.findAll();
    }

    public Curso findById(Integer id){
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    public Curso update(Integer id, Curso curso){
        Curso update = findById(id);

        if(curso.getNome() != null && !curso.getNome().isBlank()){
            update.setNome(curso.getNome());
        }

        if(curso.getDuracao() != 0){
            update.setDuracao(curso.getDuracao());
        }

        return cursoRepository.save(update);
    }

    public void delete(Integer id) {
        Curso delete = findById(id);
        cursoRepository.delete(delete);
    }
}