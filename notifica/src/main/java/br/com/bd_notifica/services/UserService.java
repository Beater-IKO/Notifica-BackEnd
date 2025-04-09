package br.com.bd_notifica.services;

import java.time.LocalDate;
import java.util.Scanner;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;

public class UserService {
    static Scanner input = new Scanner(System.in);

   public static void login(){
        UserRepository userRepository = new UserRepository();
        UserEntity user = new UserEntity();

        int op;
        do {
            System.out.println("1 - Login");
            System.out.println("2 - Registro");
            System.out.println("3 - sair");
            System.out.print("Escolha uma option: ");
            op = input.nextInt();

            switch (op) {
                case 1 -> {
                    System.out.print("Insira seu email: ");
                    String email = input.next();
                    System.out.print("Insira sua senha: ");
                    String password = input.next();
                    userRepository.findByEmailAndPassword(email, password);
                }
                case 2 -> {
                    System.out.print("Insira seu nome: ");
                    String name = input.next();
                    System.out.print("Insira seu email: ");
                    String email = input.next();
                    System.out.print("Insira sua senha: ");
                    String password = input.next();
                    System.out.print("Insira seu tipo de usuario: ");
                    System.out.println("1 - ADMIN");
                    System.out.println("2 - ALUNO");   
                    System.out.println("3 - AGENTE");     
                    int tipo = input.nextInt();

                    UserRole role = null;

                    switch (tipo) {
                        case 1 -> role = UserRole.ADMIN;
                        case 2 -> role = UserRole.STUDENT;
                        case 3 -> role = UserRole.AGENT;
                        default -> System.out.println("Tipo de usuario invalido!");
                    }

                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setRole(role);
                    user.setCreateOnDate(LocalDate.now());
                    userRepository.createUser(user);
                    System.out.println("Usuario criado com sucesso!");
                }
                case 3 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }

        } while (op != 3);
    }

}
