package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.repositories.CategoriaRepository;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.config.RecursoNaoEncontradoException;
import br.com.bd_notifica.config.RegraDeNegocioException;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final TicketRepository ticketRepository; // <<< Adicionamos dependência para validar a exclusão

    public CategoriaService(CategoriaRepository categoriaRepository, TicketRepository ticketRepository) {
        this.categoriaRepository = categoriaRepository;
        this.ticketRepository = ticketRepository;
    }

    // Salvar categoria
    public Categoria save(Categoria categoria) {
        // Validação de entrada
        if (categoria == null || categoria.getNome() == null) {
            throw new IllegalArgumentException("Categoria e nome não podem ser nulos");
        }
        
        // Validação de Regra de Negócio: Evitar nomes duplicados
        if (!categoriaRepository.findByNomeIgnoreCase(categoria.getNome()).isEmpty()) {
            throw new RegraDeNegocioException("Já existe uma categoria com o nome '" + categoria.getNome() + "'");
        }
        return categoriaRepository.save(categoria);
    }

    // Listar todas as categorias
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    // Buscar por ID
    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id)
                // <<< Lança a exceção correta (404 Not Found) quando não encontra
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com ID " + id + " não encontrada"));
    }

    // Buscar por nome
    public List<Categoria> findByNome(String nome) {
        return categoriaRepository.findByNomeIgnoreCase(nome); // Busca exata por nome (case-insensitive)
    }

    // Excluir categoria
    public void delete(Integer id) {
        // Primeiro, verifica se a categoria existe (reusa o findById que já lança
        // exceção se não existir)
        Categoria categoria = findById(id);

        // Validação de Regra de Negócio: Não permitir excluir categoria em uso
        if (!ticketRepository.findByCategoriaId(id).isEmpty()) {
            throw new RegraDeNegocioException("Não é possível excluir a categoria '" + categoria.getNome()
                    + "' pois ela já está sendo utilizada em tickets.");
        }

        categoriaRepository.delete(categoria);
    }
}