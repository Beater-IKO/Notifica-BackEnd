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
        service.criarTicketsPadrao(); // Garante que há tickets para exibir

        int opcao;

        do {
            System.out.println("\n==== MENU DO ALUNO ====\n");
            System.out.println("1 - Criar Ticket");
            System.out.println("2 - Listar Meus Tickets");
            System.out.println("3 - Buscar Ticket por ID (Meus Tickets)");
            System.out.println("4 - Deletar Ticket (Meus Tickets)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> {
                    Ticket ticket = new Ticket();

                    System.out.print("Descrição do Ticket: ");
                    ticket.setDescricao(scanner.nextLine());

                    System.out.print("Sala/Local: ");
                    ticket.setSala(scanner.nextLine());

                    System.out.println("Selecione a Área:");
                    System.out.println("1 - " + Area.INTERNA.getDescricao());
                    System.out.println("2 - " + Area.EXTERNA.getDescricao());
                    System.out.print("Opção de Área: ");
                    int areaOP = scanner.nextInt();
                    scanner.nextLine();
                    ticket.setArea(Area.fromOpcao(areaOP));

                    System.out.println("Selecione a Prioridade:");
                    System.out.println("1 - " + Prioridade.GRAU_LEVE.getDescricao());
                    System.out.println("2 - " + Prioridade.GRAU_MEDIO.getDescricao());
                    System.out.println("3 - " + Prioridade.GRAU_ALTO.getDescricao());
                    System.out.println("4 - " + Prioridade.GRAU_URGENTE.getDescricao());
                    System.out.print("Opção de Prioridade: ");
                    int prioridadeOp = scanner.nextInt();
                    scanner.nextLine();
                    ticket.setPrioridade(Prioridade.fromOpcao(prioridadeOp));
                    
                    ticket.setUser(userLogado); // Associa o ticket ao usuário logado

                    service.criarTicket(ticket);
                    System.out.println("✅ Ticket criado com sucesso!");
                }

                case 2 -> {
                    System.out.println("\n==== Seus Tickets ====\n");
                    // A CORREÇÃO ESTÁ AQUI: mudei de 'listarPorUsuario' para 'buscarPorUsuario'
                    service.buscarPorId(userLogado).forEach(System.out::println);
                    if (service.buscarPorId(userLogado).isEmpty()) {
                        System.out.println("Nenhum ticket encontrado para você.");
                    }
                }
                case 3 -> {
                    System.out.print("Digite o ID do Ticket que deseja buscar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine(); // Consumir a quebra de linha

                    Ticket t = service.buscarPorId(id);

                    // Verifica se o ticket existe e se pertence ao usuário logado
                    if (t != null && t.getUser() != null && t.getUser().getId().equals(userLogado.getId())) {
                        System.out.println("\nTicket Encontrado:\n" + t);
                    } else {
                        System.out.println("Ticket não encontrado ou não pertence a você.");
                    }
                }

                case 4 -> {
                    System.out.print("Digite o ID do Ticket que deseja deletar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine(); // Consumir a quebra de linha

                    Ticket t = service.buscarPorId(id);

                    // Verificar se o ticket existe e se pertence ao usuário logado
                    if (t != null && t.getUser() != null && t.getUser().getId().equals(userLogado.getId())) {
                        service.deletar(id);
                        System.out.println("✅ Ticket deletado com sucesso.");
                    } else {
                        System.out.println("❌ Não foi possível deletar o ticket. Ele pode não ter sido encontrado ou não pertence a você.");
                    }
                }

                case 0 -> System.out.println("Saindo do Menu do Aluno...");
                default -> System.out.println("🚫 Opção inválida. Por favor, escolha uma opção válida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}