package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.TicketService;

import java.util.List;
import java.util.Scanner;

public class AgenteDeCampo {

    public static void executarMenuAgente() {
        Scanner scanner = new Scanner(System.in);
        TicketService ticketService = new TicketService(new TicketRepository(), null);

        // Criação automática de tickets padrão
        ticketService.criarTicketsPadrao();

        int opcao;
        do {
            System.out.println("\n=== MENU AGENTE DE CAMPO ===");
            System.out.println("1 - Visualizar Tickets Pendentes");
            System.out.println("2 - Visualizar Tickets Finalizados");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    visualizarETrocarStatus(ticketService, scanner);
                    break;
                case 2:
                    listarTickets(ticketService, "Finalizado");
                    break;
                case 3:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 3);

        scanner.close();
    }

    private static void listarTickets(TicketService service, String status) {
        List<Ticket> tickets = service.buscarPorStatus(status);
        if (tickets.isEmpty()) {
            System.out.println("Nenhum ticket com status: " + status);
            return;
        }

        System.out.println("\nTickets com status: " + status);
        for (Ticket t : tickets) {
            System.out.println("ID: " + t.getId() + " - " + t.getDescricao());
        }
    }

    private static void visualizarETrocarStatus(TicketService service, Scanner scanner) {
        List<Ticket> ticketsPendentes = service.buscarPorStatus("Pendente");

        // Inclui também os tickets "Lido" e "Em andamento" como parte dos pendentes
        ticketsPendentes.addAll(service.buscarPorStatus("Lido"));
        ticketsPendentes.addAll(service.buscarPorStatus("Em andamento"));

        if (ticketsPendentes.isEmpty()) {
            System.out.println("Não há tickets pendentes.");
            return;
        }

        System.out.println("\nTickets Pendentes:");
        for (Ticket t : ticketsPendentes) {
            System.out
                    .println("ID: " + t.getId() + " - " + t.getDescricao() + " (Status atual: " + t.getStatus() + ")");
        }

        System.out.print("Digite o ID do ticket que deseja atualizar (ou 0 para voltar): ");
        Long id = scanner.nextLong();
        if (id == 0)
            return;

        Ticket ticket = service.buscarPorId(id);
        if (ticket == null) {
            System.out.println("Ticket não encontrado.");
            return;
        }

        System.out.println("Escolha o novo status:");
        System.out.println("1 - Lido");
        System.out.println("2 - Em andamento");
        System.out.println("3 - Finalizado");
        System.out.print("Opção: ");
        int opcaoStatus = scanner.nextInt();

        switch (opcaoStatus) {
            case 1:
                ticket.setStatus("Lido");
                break;
            case 2:
                ticket.setStatus("Em andamento");
                break;
            case 3:
                ticket.setStatus("Finalizado");
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        service.editar(ticket);
        System.out.println("Status atualizado com sucesso.");
    }
}
