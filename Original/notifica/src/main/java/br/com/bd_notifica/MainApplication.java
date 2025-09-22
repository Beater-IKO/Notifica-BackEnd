package br.com.bd_notifica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Classe principal da aplicação Spring Boot para o sistema Notifica
 * Gerencia tickets, usuários, salas e notificações
 */
@SpringBootApplication
@EnableTransactionManagement
public class MainApplication {

  // método principal que inicia a aplicação
  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }
}