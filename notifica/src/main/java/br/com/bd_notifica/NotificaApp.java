package br.com.bd_notifica;

import br.com.bd_notifica.view.LoginView;

import javax.swing.*;

/**
 * Classe principal para iniciar o aplicativo Notifica.
 */
public class NotificaApp {
    
    public static void main(String[] args) {
        // Inicia a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Cria e exibe a tela de login
                LoginView loginView = new LoginView();
                loginView.setLocationRelativeTo(null); // Centraliza na tela
                loginView.setVisible(true);
                
                System.out.println("Aplicativo Notifica iniciado com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Erro ao iniciar o aplicativo: " + e.getMessage(),
                    "Erro de Inicialização",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}