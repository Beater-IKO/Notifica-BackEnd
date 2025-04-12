package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.TicketRepository;

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

    public List<Ticket> buscarPorAlunoId(Long alunoId) {
        return ticketRepository.buscarPorAlunoId(alunoId);
    }

    public List<Ticket> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        return ticketRepository.buscarPorIntervalo(inicio, fim);
    }

    public List<Ticket> buscarPorStatus(String status) {
        return ticketRepository.buscarPorStatus(status);
    }

    public void criarTicketsPadrao() {
        Ticket t1 = new Ticket(null, "Internet não funciona", Area.INTERNA, "Lab 1", Prioridade.GRAU_ALTO,
                LocalDate.now(), "Pendente");
        t1.setAlunoId(1L);

        Ticket t2 = new Ticket(null, "Cadeira quebrada", Area.EXTERNA, "Sala 201", Prioridade.GRAU_LEVE,
                LocalDate.now().minusDays(2), "Pendente");
        t2.setAlunoId(2L);

        Ticket t3 = new Ticket(null, "Projetor queimado", Area.INTERNA, "Sala 105", Prioridade.GRAU_URGENTE,
                LocalDate.now().minusDays(5), "Pendente");
        t3.setAlunoId(3L);

        ticketRepository.salvar(t1);
        ticketRepository.salvar(t2);
        ticketRepository.salvar(t3);
    }
}
