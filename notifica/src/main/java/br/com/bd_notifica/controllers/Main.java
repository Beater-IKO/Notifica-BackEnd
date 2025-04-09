package br.com.bd_notifica.controllers;

import java.util.Scanner;


import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.TicketService;

public class Main{

    public static void main(String[] args) {
        TicketService service = new TicketService(new TicketRepository());
        Scanner scanner = new Scanner(System.in);
        
        int opcao;
    

    do{

            System.out.println("\n====MENU===\n");
            System.out.println("1- Criar Ticket\n");
            System.out.println("2- Listar Ticket\n");
            System.out.println("4- Buscar Ticket\n");
            System.out.println("0- Sair");
            opcao = scanner.nextInt();
            scanner.nextLine();


            switch (opcao) {
               case 1 ->{
                Ticket ticket = new Ticket(); 

                    System.out.println("Descrição: "); 
                    ticket.setDescricao(scanner.nextLine());

                    System.out.println("Sala: ");
                    ticket.setSala(scanner.nextLine());

                    System.out.println("Área (1- INTERNA/2- EXTERNA)");
                    ticket.setArea(Area.valueOf(scanner.nextLine().toUpperCase()));

                    System.out.println("Prioridade: ");
                    ticket.setPrioridade(Prioridade.valueOf(scanner.nextLine().toUpperCase()));

                    service.criarTicket(ticket);
                    System.out.println("✅ Ticket criado!");

               
                  }

                  case 2 -> service.listarTodos().forEach(System.out::println);

                  case 3 ->{
                        System.out.println("ID: ");
                        Long id = scanner.nextLong();
                        Ticket t = service.buscarPorId(id);
                        System.out.println(t != null ? t : "❌ Não encontrado");

                  }

                  case 4 ->{
                    System.out.println("Id para deletar: ");
                    Long id = scanner.nextLong();
                    service.deletar(id);
                    System.out.println("🗑️ Ticket deletado.");
                  }

                  case 0 -> System.out.println("Saindo...");
                  default -> System.out.println("Opcao Inválida");
                
            }


    }while( opcao != 0);


    scanner.close();
}
}