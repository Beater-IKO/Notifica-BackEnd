package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.services.TicketService;

import java.time.LocalDate;
import java.util.Scanner;

public class AdminTicketController {

    public static void menuAdm(TicketService service) {
        Scanner sc = new Scanner(System.in);
        int op;
        service.criarTicketsPadrao(); // cria tickets ao iniciar

        do {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1 - Criar Ticket");
            System.out.println("2 - Listar Todos");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Editar Ticket");
            System.out.println("5 - Deletar Ticket");
            System.out.println("6 - Buscar por ID de Aluno");
            System.out.println("7 - Buscar por intervalo de datas");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    Ticket t = new Ticket();
                    System.out.print("Descrição: ");
                    t.setDescricao(sc.nextLine());
                    System.out.print("Sala: ");
                    t.setSala(sc.nextLine());
                    System.out.print("Área (INTERNA/EXTERNA): ");
                    t.setArea(Area.valueOf(sc.nextLine().toUpperCase()));
                    System.out.print("Prioridade (GRAU_LEVE/..): ");
                    t.setPrioridade(Prioridade.valueOf(sc.nextLine().toUpperCase()));
                    System.out.print("ID do aluno: ");
                    t.setAlunoId(sc.nextLong());

                    service.criarTicket(t);
                    System.out.println("✅ Ticket criado.");
                }

                case 2 -> service.listarTodos().forEach(System.out::println);

                case 3 -> {
                    System.out.print("ID do ticket: ");
                    Long id = sc.nextLong();
                    Ticket encontrado = service.buscarPorId(id);
                    System.out.println(encontrado != null ? encontrado : "❌ Não encontrado.");
                }

                case 4 -> {
                    System.out.print("ID para editar: ");
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
                        System.out.println("✏️ Editado com sucesso!");
                    } else {
                        System.out.println("❌ Ticket não encontrado.");
                    }
                }

                case 5 -> {
                    System.out.print("ID para deletar: ");
                    Long id = sc.nextLong();
                    service.deletar(id);
                    System.out.println("🗑️ Ticket deletado.");
                }

                case 6 -> {
                    System.out.print("ID do aluno: ");
                    Long alunoId = sc.nextLong();
                    service.buscarPorAlunoId(alunoId).forEach(System.out::println);
                }

                case 7 -> {
                    System.out.print("Data início (AAAA-MM-DD): ");
                    LocalDate inicio = LocalDate.parse(sc.next());
                    System.out.print("Data fim (AAAA-MM-DD): ");
                    LocalDate fim = LocalDate.parse(sc.next());
                    service.buscarPorIntervalo(inicio, fim).forEach(System.out::println);
                }

                case 0 -> System.out.println("Saindo...");

                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }
}
