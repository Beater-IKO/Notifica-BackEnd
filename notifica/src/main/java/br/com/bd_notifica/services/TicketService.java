package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket criarTicket(Ticket ticket) {
        ticket.setDataCriacao(LocalDate.now());
        ticket.setStatus("Pendente"); // Definindo o status como "Pendente" ao criar o ticket
        return ticketRepository.salvar(ticket);
    }

    public List<Ticket> listarTodos() {
        return ticketRepository.listarTodos();
    }

    public Ticket buscarPorId(Long id) {
        return ticketRepository.buscarPorId(id);
    }

    public void deletar(Long id) {
        ticketRepository.deletar(id);
    }

    public Ticket editar(Ticket ticket) {
        return ticketRepository.editar(ticket);
    }

    public List<Ticket> listarPorUsuario(UserEntity user) {
        return ticketRepository.buscarPorUsuario(user);
    }

    public List<Ticket> buscarPorAlunoId(Long alunoId) {
        UserService userService = new UserService(new UserRepository());
        UserEntity user = userService.buscarPorId(alunoId);
        return ticketRepository.buscarPorAlunoId(user);
    }

    public List<Ticket> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        return ticketRepository.buscarPorIntervalo(inicio, fim);
    }

    public List<Ticket> buscarPorStatus(String status) {
        return ticketRepository.buscarPorStatus(status);
    }

    public void criarTicketsPadrao() {
        UserRepository userRepository = new UserRepository();
        UserEntity user1 = userRepository.findById(1L);
        UserEntity user2 = userRepository.findById(2L);
        UserEntity user3 = userRepository.findById(3L);

        Ticket t1 = new Ticket(null, "Internet não funciona", Area.INTERNA, "Lab 1", Prioridade.GRAU_ALTO,
                LocalDate.now(), "Pendente", null);
        t1.setUser(user1);

        Ticket t2 = new Ticket(null, "Cadeira quebrada", Area.EXTERNA, "Sala 201", Prioridade.GRAU_LEVE,
                LocalDate.now().minusDays(2), "Pendente", null);
        t2.setUser(user2);

        Ticket t3 = new Ticket(null, "Projetor queimado", Area.INTERNA, "Sala 105", Prioridade.GRAU_URGENTE,
                LocalDate.now().minusDays(5), "Pendente", null);
        t3.setUser(user3);

        ticketRepository.salvar(t1);
        ticketRepository.salvar(t2);
        ticketRepository.salvar(t3);
    }
}
