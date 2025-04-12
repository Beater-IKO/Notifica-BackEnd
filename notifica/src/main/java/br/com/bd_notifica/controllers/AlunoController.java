package br.com.bd_notifica.controllers;

import java.util.Scanner;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.TicketService;

public class AlunoController {

    public static void menuAluno(String[] args) {
        TicketService service = new TicketService(new TicketRepository());
        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("\n==== MENU ====\n");
            System.out.println("1 - Criar Ticket");
            System.out.println("2 - Listar Tickets");
            System.out.println("3 - Buscar Ticket");
            System.out.println("4 - Deletar Ticket");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    Ticket ticket = new Ticket();

                    System.out.println("Descrição: ");
                    ticket.setDescricao(scanner.nextLine());

                    System.out.println("Sala: ");
                    ticket.setSala(scanner.nextLine());

                    System.out.println("Área:");
                    System.out.println("1 - " + Area.INTERNA.getDescricao());
                    System.out.println("2 - " + Area.EXTERNA.getDescricao());
                    int areaOP = scanner.nextInt();
                    scanner.nextLine();
                    ticket.setArea(Area.fromOpcao(areaOP));

                    System.out.println("Prioridade:");
                    System.out.println("1 - " + Prioridade.GRAU_LEVE.getDescricao());
                    System.out.println("2 - " + Prioridade.GRAU_MEDIO.getDescricao());
                    System.out.println("3 - " + Prioridade.GRAU_ALTO.getDescricao());
                    System.out.println("4 - " + Prioridade.GRAU_URGENTE.getDescricao());
                    int prioridadeOp = scanner.nextInt();
                    scanner.nextLine();
                    ticket.setPrioridade(Prioridade.fromOpcao(prioridadeOp));

                    service.criarTicket(ticket);
                    System.out.println(" Ticket criado!");
                }

                case 2 -> service.listarTodos().forEach(System.out::println);

                case 3 -> {
                    System.out.println("ID: ");
                    Long id = scanner.nextLong();
                    Ticket t = service.buscarPorId(id);
                    System.out.println(t != null ? t : " Não encontrado");
                }

                case 4 -> {
                    System.out.println("ID para deletar: ");
                    Long id = scanner.nextLong();
                    service.deletar(id);
                    System.out.println(" Ticket deletado.");
                }

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println(" Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}