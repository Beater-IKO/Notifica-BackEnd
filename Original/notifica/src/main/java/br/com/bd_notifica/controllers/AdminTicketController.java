package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.services.TicketService;
import br.com.bd_notifica.services.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class AdminTicketController {

    public static void menuAdm(TicketService service, UserService userService, UserEntity usuarioLogado) {
        Scanner sc = new Scanner(System.in);
        int op;

        // Cria 3 tickets automaticamente no início
        service.criarTicketsPadrao();

        do {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1 - Criar Ticket");
            System.out.println("2 - Listar Todos os Tickets");
            System.out.println("3 - Buscar por ID do Ticket");
            System.out.println("4 - Editar Ticket");
            System.out.println("5 - Deletar Ticket");
            System.out.println("6 - Buscar Tickets por ID do Aluno");
            System.out.println("7 - Buscar Tickets por Intervalo de Datas");
            System.out.println("8 - Buscar Tickets por Tipo de Usuário (Role)");
            System.out.println("9 - Buscar Tickets por Nome do Usuário (parcial)");
            System.out.println("10 - Contar Tickets por Status");
            System.out.println("0 - Sair");
            System.out.print("Escolha a opção: ");
            op = sc.nextInt();
            sc.nextLine(); // limpa buffer

            switch (op) {
                case 1 -> {
                    Ticket t = new Ticket();
                    System.out.print("Descrição: ");
                    t.setDescricao(sc.nextLine());
                    System.out.print("Sala: ");
                    t.setSala(sc.nextLine());
                    System.out.print("Área (INTERNA/EXTERNA): ");
                    t.setArea(Area.valueOf(sc.nextLine().toUpperCase()));
                    System.out.print("Prioridade (GRAU_LEVE/GRAU_MEDIO/...): ");
                    t.setPrioridade(Prioridade.valueOf(sc.nextLine().toUpperCase()));
                    t.setUser(usuarioLogado); // Vincula o usuário admin ao ticket

                    service.criarTicket(t);
                    System.out.println("Ticket criado com sucesso!");
                }

                case 2 -> {
                    System.out.println("=== Lista de Todos os Tickets ===");
                    service.listarTodos().forEach(System.out::println);
                }

                case 3 -> {
                    System.out.print("Digite o ID do ticket: ");
                    Long id = sc.nextLong();
                    Ticket encontrado = service.buscarPorId(id);
                    System.out.println(encontrado != null ? encontrado : "Ticket não encontrado.");
                }

                case 4 -> {
                    System.out.print("Digite o ID do ticket que deseja editar: ");
                    Long id = sc.nextLong();
                    sc.nextLine();
                    Ticket t = service.buscarPorId(id);
                    if (t != null) {
                        System.out.print("Nova descrição: ");
                        t.setDescricao(sc.nextLine());
                        System.out.print("Nova sala: ");
                        t.setSala(sc.nextLine());
                        System.out.print("Nova área (INTERNA/EXTERNA): ");
                        t.setArea(Area.valueOf(sc.nextLine().toUpperCase()));
                        System.out.print("Nova prioridade: ");
                        t.setPrioridade(Prioridade.valueOf(sc.nextLine().toUpperCase()));
                        service.editar(t);
                        System.out.println("Ticket editado com sucesso!");
                    } else {
                        System.out.println("Ticket não encontrado.");
                    }
                }

                case 5 -> {
                    System.out.print("Digite o ID do ticket para deletar: ");
                    Long id = sc.nextLong();
                    service.deletar(id);
                    System.out.println("Ticket deletado.");
                }

                case 6 -> {
                    System.out.print("Digite o ID do aluno: ");
                    Long alunoId = sc.nextLong();
                    service.buscarPorAlunoId(alunoId).forEach(System.out::println);
                }

                case 7 -> {
                    System.out.print("Data inicial (AAAA-MM-DD): ");
                    LocalDate inicio = LocalDate.parse(sc.next());
                    System.out.print("Data final (AAAA-MM-DD): ");
                    LocalDate fim = LocalDate.parse(sc.next());
                    service.buscarPorIntervalo(inicio, fim).forEach(System.out::println);
                }

                case 8 -> {
                    System.out.print("Tipo de usuário (ADMIN, STUDENT, AGENT): ");
                    String tipo = sc.nextLine().toUpperCase();
                    try {
                        service.buscarPorTipoUsuario(tipo).forEach(System.out::println);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de usuário inválido!");
                    }
                }

                case 9 -> {
                    System.out.print("Parte do nome do usuário: ");
                    String nomeParcial = sc.nextLine();
                    service.buscarPorNomeUsuario(nomeParcial).forEach(System.out::println);
                }

                case 10 -> {
                    System.out.println("Quantidade de Tickets por Status:");
                    service.contarChamadosPorStatus()
                            .forEach(registro ->
                                    System.out.println("Status: " + registro[0] + " | Quantidade: " + registro[1])
                            );
                }

                case 0 -> System.out.println("Saindo do menu admin...");

                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
        sc.close();
    }
}
