package br.com.bd_notifica.view;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.TicketService;
import br.com.bd_notifica.services.UserService;

import javax.swing.*;

/**
 * Classe utilitária para iniciar o painel de administração.
 */
public class AdminPanelLauncher {

    /**
     * Inicia o painel de administração para o usuário logado.
     * 
     * @param user O usuário logado
     */
    public static void launchAdminPanel(UserEntity user) {
        try {
            // Inicializa os repositórios e serviços
            TicketRepository ticketRepository = new TicketRepository();
            UserRepository userRepository = new UserRepository();
            TicketService ticketService = new TicketService(ticketRepository, userRepository);
            UserService userService = new UserService(userRepository);
            
            // Cria e exibe a janela de administração
            JFrame adminFrame = new JFrame("Painel do Administrador");
            adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            adminFrame.setSize(900, 600);
            
            // Cria o painel de administrador
            AdminTicketPanel adminPanel = new AdminTicketPanel(ticketService, userService, user);
            adminFrame.add(adminPanel);
            adminFrame.setLocationRelativeTo(null);
            adminFrame.setVisible(true);
            
            System.out.println("Painel de administração iniciado para: " + user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Erro ao iniciar o painel de administração: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}