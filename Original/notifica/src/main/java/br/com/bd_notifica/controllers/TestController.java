package br.com.bd_notifica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller para testes da API
@RestController
@RequestMapping("/test")
public class TestController {

    // Teste básico da API
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API funcionando!");
    }
    
    // Endpoint de teste simples
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}