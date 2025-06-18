package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.TicketService;
import java.time.LocalDate;
// import java.util.List; // Não é necessário para a funcionalidade de criar apenas

public class AlunoController {

    private final TicketService ticketService;
    private final UserEntity loggedInUser;

    public AlunoController(UserEntity loggedInUser) {
        this.loggedInUser = loggedInUser;
        TicketRepository ticketRepository = new TicketRepository();
        UserRepository userRepository = new UserRepository();
        this.ticketService = new TicketService(ticketRepository, userRepository);
    }

    /**
     * Cria um novo ticket para o aluno logado e o persiste no banco de dados.
     * @param descricao A descrição completa do problema.
     * @param sala A sala onde o problema ocorreu.
     * @param area A área (Interna/Externa) do problema.
     * @param prioridade O grau de prioridade do ticket.
     * @return true se o ticket foi criado com sucesso, false caso contrário.
     */
    public boolean criarTicket(String descricao, String sala, Area area, Prioridade prioridade) {
        try {
            Ticket novoTicket = new Ticket();
            novoTicket.setDescricao(descricao);
            novoTicket.setSala(sala);
            novoTicket.setArea(area);
            novoTicket.setPrioridade(prioridade);
            novoTicket.setUser(loggedInUser);
            novoTicket.setDataCriacao(LocalDate.now());
            novoTicket.setStatus("Pendente");

            ticketService.criarTicket(novoTicket);
            System.out.println("Ticket criado pelo aluno com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao criar ticket pelo aluno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}