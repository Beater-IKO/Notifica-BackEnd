package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.enums.UserRole; // !!! IMPORTANTE: Verifique se esta linha está aqui !!!
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TicketService {

    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = new UserRepository(); // Inicializa o userRepository automaticamente
    }
    
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket criarTicket(Ticket ticket) {
        ticket.setDataCriacao(LocalDate.now());
        if (ticket.getStatus() == null || ticket.getStatus().isEmpty()) {
            ticket.setStatus("Pendente"); // Status padrão ao criar um ticket
        }
        return ticketRepository.salvar(ticket);
    }

    public List<Ticket> listarTodos() {
        return ticketRepository.listarTodos();
    }

    public List<Ticket> buscarTicketsPorUsuario(UserEntity userLogado) {
        return ticketRepository.buscarPorUsuario(userLogado);
    }

    public Ticket editar(Ticket ticket) {
        return ticketRepository.editar(ticket);
    }

    public void deletar(Long id) {
        ticketRepository.deletar(id);
    }

    public List<Ticket> buscarPorAlunoId(Long alunoId) {
        UserEntity user = userRepository.findById(alunoId);
        if (user != null) {
            return ticketRepository.buscarPorUsuario(user);
        }
        return List.of(); // Retorna lista vazia se o usuário não for encontrado
    }

    public List<Ticket> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        return ticketRepository.buscarPorIntervalo(inicio, fim);
    }

    public List<Ticket> buscarPorStatus(String status) {
        return ticketRepository.buscarPorStatus(status);
    }

    public List<Object[]> contarChamadosPorStatus() {
        return ticketRepository.contarChamadosPorStatus();
    }

    public List<Ticket> buscarPorTipoUsuario(String role) {
        try {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            return ticketRepository.buscarChamadosPorTipoUsuario(userRole);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuário inválido: " + role);
        }
    }

    public List<Ticket> buscarPorNomeUsuario(String nomeParcial) {
        return ticketRepository.buscarPorNomeUsuario(nomeParcial);
    }

    // Método para criar tickets padrão (para testes ou inicialização)
     public void criarTicketsPadrao() {
        try {
            // Verifica se já existem tickets para evitar duplicação em cada execução do menu
            if (ticketRepository.listarTodos().isEmpty()) {
                // Usa o usuário logado para criar os tickets
                UserEntity adminUser = userRepository.findByEmail("admin@example.com");
                
                if (adminUser == null) {
                    System.out.println("Usuário admin não encontrado. Os tickets serão criados sem usuário associado.");
                }
    
                Ticket t1 = new Ticket();
                t1.setDescricao("Problema com a impressora do Lab 1");
                t1.setSala("Laboratório 1");
                t1.setArea(Area.INTERNA);
                t1.setPrioridade(Prioridade.GRAU_ALTO);
                t1.setDataCriacao(LocalDate.now());
                t1.setStatus("Pendente");
                t1.setUser(adminUser);
                ticketRepository.salvar(t1);
    
                Ticket t2 = new Ticket();
                t2.setDescricao("Lâmpada queimada na sala 205");
                t2.setSala("Sala 205");
                t2.setArea(Area.INTERNA);
                t2.setPrioridade(Prioridade.GRAU_MEDIO);
                t2.setDataCriacao(LocalDate.now());
                t2.setStatus("Em andamento");
                t2.setUser(adminUser);
                ticketRepository.salvar(t2);
    
                Ticket t3 = new Ticket();
                t3.setDescricao("Vazamento no banheiro masculino do térreo");
                t3.setSala("Banheiro Térreo");
                t3.setArea(Area.EXTERNA);
                t3.setPrioridade(Prioridade.GRAU_URGENTE);
                t3.setDataCriacao(LocalDate.now());
                t3.setStatus("Finalizado");
                t3.setUser(adminUser);
                ticketRepository.salvar(t3);
                
                System.out.println("Tickets padrão criados com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar tickets padrão: " + e.getMessage());
            e.printStackTrace();
        }
    }

     public Object listarPorUsuario(UserEntity userLogado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorUsuario'");
     }

     public Ticket buscarPorId(Long id) {
        return ticketRepository.buscarPorId(id);
     }
}       