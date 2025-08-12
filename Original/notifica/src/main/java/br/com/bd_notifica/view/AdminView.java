package br.com.bd_notifica.view;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.TicketService;
import br.com.bd_notifica.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Janela principal para o painel de administração.
 */
public class AdminView extends JFrame {

    private static final long serialVersionUID = 1L;
    private AdminTicketPanel adminTicketPanel;
    private UserEntity loggedInUser;

    /**
     * Cria uma nova janela de administração.
     * 
     * @param userService Serviço de usuários
     * @param loggedInUser Usuário logado
     */
    public AdminView(UserService userService, UserEntity loggedInUser) {
        this.loggedInUser = loggedInUser;
        
        System.out.println("Criando AdminView para usuário: " + loggedInUser.getName());
        
        // Configuração da janela
        setTitle("Painel de Administração - " + loggedInUser.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Inicializa os serviços necessários
        TicketRepository ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketService(ticketRepository);
        
        // Cria o painel de administrador
        adminTicketPanel = new AdminTicketPanel(ticketService, userService, loggedInUser);
        
        // Adiciona o painel à janela
        getContentPane().add(adminTicketPanel, BorderLayout.CENTER);
        
        // Adiciona uma barra de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem logoutItem = new JMenuItem("Sair");
        logoutItem.addActionListener(e -> logout());
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        // Adiciona um listener para quando a janela for fechada
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logout();
            }
        });
    }
    
    /**
     * Realiza o logout e retorna para a tela de login.
     */
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente sair?",
            "Confirmar Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            LoginView loginView = new LoginView();
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        }
    }
}