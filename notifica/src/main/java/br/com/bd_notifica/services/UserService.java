package br.com.bd_notifica.services;

import java.time.LocalDate;
import java.util.Scanner;

import br.com.bd_notifica.controllers.AdminTicketController;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;

public class UserService {
    static Scanner input = new Scanner(System.in);

    public static void login() {
        UserRepository userRepository = new UserRepository();
        UserEntity user = null;

        int op;
        do {
            System.out.println("1 - Login");
            System.out.println("2 - Registro");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            op = input.nextInt();
            input.nextLine(); // Limpa quebra de linha

            switch (op) {
                case 1 -> {
                    System.out.print("Insira seu email: ");
                    String email = input.nextLine();
                    System.out.print("Insira sua senha: ");
                    String password = input.nextLine();

                    user = userRepository.findByEmailAndPassword(email, password);

                    if (user != null) {
                        System.out.println("✅ Login bem-sucedido como: " + user.getRole());
                        if (user.getRole() == UserRole.ADMIN) {
                            // Redireciona para o menu do ADM
                            TicketService service = new TicketService(new TicketRepository());
                            AdminTicketController.menuAdm(service);
                        } else {
                            System.out.println("🔒 Apenas administradores podem acessar o painel de tickets.");
                        }
                    } else {
                        System.out.println("❌ Email ou senha incorretos.");
                    }
                }

                case 2 -> {
                    System.out.print("Insira seu nome: ");
                    String name = input.nextLine();
                    System.out.print("Insira seu email: ");
                    String email = input.nextLine();
                    System.out.print("Insira sua senha: ");
                    String password = input.nextLine();
                    System.out.println("Insira seu tipo de usuário: ");
                    System.out.println("1 - ADMIN");
                    System.out.println("2 - ALUNO");
                    System.out.println("3 - AGENTE");
                    int tipo = input.nextInt();
                    input.nextLine();

                    UserRole role = switch (tipo) {
                        case 1 -> UserRole.ADMIN;
                        case 2 -> UserRole.STUDENT;
                        case 3 -> UserRole.AGENT;
                        default -> null;
                    };

                    if (role != null) {
                        user = new UserEntity();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setRole(role);
                        user.setCreateOnDate(LocalDate.now());

                        userRepository.createUser(user);
                        System.out.println("✅ Usuário criado com sucesso!");
                    } else {
                        System.out.println("❌ Tipo de usuário inválido!");
                    }
                }

                case 3 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

        } while (op != 3);
    }
}
