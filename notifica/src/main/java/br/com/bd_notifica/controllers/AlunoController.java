package br.com.bd_notifica.controllers;

import java.util.Scanner;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.services.TicketService;
import br.com.bd_notifica.services.UserService;

public class AlunoController {

    public static void menuAluno(UserService userService, TicketService service, UserEntity userLogado) {
        Scanner scanner = new Scanner(System.in);
        service.criarTicketsPadrao();

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
                    ticket.setUser(userLogado);

                    service.criarTicket(ticket);
                    System.out.println(" Ticket criado!");
                }

                case 2 -> {
                    System.out.println("Seus Tickets: ");
                    service.listarPorUsuario(userLogado).forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("ID do Ticket: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    Ticket t = service.buscarPorId(id);

                    // Verifica se o ticket existe e se pertence ao usuário logado
                    if (t != null && t.getUser().getId().equals(userLogado.getId())) {
                        System.out.println(t);
                    } else {
                        System.out.println("Ticket não encontrado ou não pertence a você.");
                    }
                }

                case 4 -> {
                    System.out.println("ID do Ticket para deletar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    Ticket t = service.buscarPorId(id);

                    // Verificar se o ticket existe e se pertence ao usuário logado
                    if (t != null && t.getUser().getId().equals(userLogado.getId())) {
                        service.deletar(id);
                        System.out.println("Ticket deletado com sucesso.");
                    } else {
                        System.out.println("Não foi possivel deletar o ticket pois não foi encontrado ou não pertence a você.");
                    }
                }

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println(" Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}