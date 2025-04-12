package br.com.bd_notifica.controllers;

import java.util.List;
import java.util.Scanner;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.repositories.TicketRepository;

public class AgenteDeCampo {

    public static void menuAgente(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicketRepository ticketRepo = new TicketRepository();
        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Visualizar Tickets Pendentes");
            System.out.println("2 - Visualizar Tickets Realizados");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // Exibindo tickets pendentes
                    List<Ticket> ticketsPendentes = ticketRepo.buscarPorStatus("Pendente");

                    if (ticketsPendentes.isEmpty()) {
                        System.out.println("Não há tickets pendentes.");
                        break;
                    }

                    System.out.println("Tickets Pendentes:");
                    for (Ticket t : ticketsPendentes) {
                        System.out.println("ID: " + t.getId() + " - " + t.getDescricao());
                    }

                    System.out.print("Digite o ID do ticket que deseja editar (ou 0 para voltar): ");
                    Long idTicket = scanner.nextLong();

                    if (idTicket == 0)
                        break;

                    Ticket ticketSelecionado = ticketRepo.buscarPorId(idTicket);

                    if (ticketSelecionado == null) {
                        System.out.println("Ticket não encontrado.");
                        break;
                    }

                    // Menu de ações
                    System.out.println("O que deseja fazer com este ticket?");
                    System.out.println("1 - Marcar como Lido");
                    System.out.println("2 - Marcar como Em andamento");
                    System.out.println("3 - Marcar como Finalizado");
                    System.out.println("4 - Voltar");

                    int acao = scanner.nextInt();

                    switch (acao) {
                        case 1:
                            ticketSelecionado.setStatus("Lido");
                            ticketRepo.editar(ticketSelecionado);
                            System.out.println("Ticket marcado como Lido.");
                            break;
                        case 2:
                            ticketSelecionado.setStatus("Em andamento");
                            ticketRepo.editar(ticketSelecionado);
                            System.out.println("Ticket marcado como Em andamento.");
                            break;
                        case 3:
                            ticketSelecionado.setStatus("Finalizado");
                            ticketRepo.editar(ticketSelecionado);
                            System.out.println("Ticket marcado como Finalizado.");
                            break;
                        case 4:
                            System.out.println("Voltando...");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                    break;

                case 2:
                    // Exibindo tickets finalizados
                    List<Ticket> finalizados = ticketRepo.buscarPorStatus("Finalizado");

                    if (finalizados.isEmpty()) {
                        System.out.println("Não há tickets finalizados.");
                        break;
                    }

                    System.out.println("Tickets Finalizados:");
                    for (Ticket t : finalizados) {
                        System.out.println("ID: " + t.getId() + " - " + t.getDescricao());
                    }
                    break;

                case 3:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 3);

        scanner.close();
    }
}
