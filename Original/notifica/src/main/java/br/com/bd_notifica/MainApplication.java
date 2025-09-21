package br.com.bd_notifica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement; // 1. IMPORTE

@SpringBootApplication
@EnableTransactionManagement // 2. ADICIONE ESTA ANOTAÇÃO
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

}