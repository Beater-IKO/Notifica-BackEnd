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

    public void criarTicketsPadrao() {
        Ticket t1 = new Ticket(null, "Internet não funciona", Area.INTERNA, "Lab 1", Prioridade.GRAU_ALTO, LocalDate.now());
        t1.setAlunoId(1L);

        Ticket t2 = new Ticket(null, "Cadeira quebrada", Area.EXTERNA, "Sala 201", Prioridade.GRAU_LEVE, LocalDate.now().minusDays(2));
        t2.setAlunoId(2L);

        Ticket t3 = new Ticket(null, "Projetor queimado", Area.INTERNA, "Sala 105", Prioridade.GRAU_MUITO_ALTO, LocalDate.now().minusDays(5));
        t3.setAlunoId(1L);

        ticketRepository.salvar(t1);
        ticketRepository.salvar(t2);
        ticketRepository.salvar(t3);
    }
}
