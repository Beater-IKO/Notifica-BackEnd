package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.config.GenericExceptions.Unauthorized;
import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.repositories.SuporteRepository;

import java.util.List;

// Serviço para suporte
@Service
public class SuporteService {
    private final SuporteRepository suporteRepository;

    public SuporteService(SuporteRepository suporteRepository) {
        this.suporteRepository = suporteRepository;
    }

    // Salvar suporte
    public Suporte save(Suporte suporte) {
        // Validação de campos obrigatórios
        if (suporte.getTitulo() == null || suporte.getTitulo().isBlank()) {
            throw new ValidationException("Título é obrigatório");
        }

        if (suporte.getDescricao() == null || suporte.getDescricao().isBlank()) {
            throw new ValidationException("Descrição é obrigatória");
        }

        // Atribui status automaticamente baseado no tipo
        if (suporte.getStatus() == null) {
            suporte.setStatus(Status.VISTO);
        }

        // Tipos críticos recebem prioridade maior
        if (suporte.getTipo() == TipoSuporte.TECNICO) {
            suporte.setStatus(Status.EM_ANDAMENTO);
        }

        return suporteRepository.save(suporte);
    }

    // Listar suportes
    public List<Suporte> findAll() {
        return suporteRepository.findAll();
    }

    // Buscar por ID
    public Suporte findById(Integer id) {
        return suporteRepository.findById(id)
                .orElseThrow(() -> new NotFound("Suporte não encontrado"));
    }

    // Filtrar por tipo
    public List<Suporte> findByTipo(TipoSuporte tipo) {
        return suporteRepository.findByTipo(tipo);
    }

    // Filtrar por status
    public List<Suporte> findByStatus(Status status) {
        return suporteRepository.findByStatus(status);
    }

    // Atualizar suporte
    public Suporte update(Integer id, Suporte suporte) {
        Suporte existingSuport = findById(id);

        // Não permite modificar suportes finalizados
        if (existingSuport.getStatus() == Status.FINALIZADOS) {
            throw new Unauthorized("Não é possível modificar suporte finalizado");
        }

        if (suporte.getTitulo() != null && !suporte.getTitulo().isBlank()) {
            existingSuport.setTitulo(suporte.getTitulo());
        }

        if (suporte.getDescricao() != null && !suporte.getDescricao().isBlank()) {
            existingSuport.setDescricao(suporte.getDescricao());
        }

        if (suporte.getTipo() != null) {
            existingSuport.setTipo(suporte.getTipo());
        }

        if (suporte.getStatus() != null) {
            existingSuport.setStatus(suporte.getStatus());
        }

        if (suporte.getUser() != null) {
            existingSuport.setUser(suporte.getUser());
        }

        return suporteRepository.save(existingSuport);
    }

    // Excluir suporte
    public void delete(Integer id) {
        Suporte suporteToDelete = findById(id);

        // Não permite excluir suportes em andamento
        if (suporteToDelete.getStatus() == Status.EM_ANDAMENTO) {
            throw new Unauthorized("Não é possível excluir suporte em andamento");
        }

        suporteRepository.delete(suporteToDelete);
    }
}