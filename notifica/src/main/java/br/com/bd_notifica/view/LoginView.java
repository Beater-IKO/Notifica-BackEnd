package br.com.bd_notifica.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.UserService;
import br.com.bd_notifica.utils.Criptografia;
import br.com.bd_notifica.view.AdminPanelLauncher;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void exibirTabela() {
		List<UserEntity> users = UserRepository.listarTodos();

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("EMAIL");
		model.addColumn("ROLE");

		for (UserEntity ue : users) {
			model.addRow(new Object[] { ue.getEmail(), ue.getRole().toString() });
		}

		JTable tabela = new JTable(model);
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(10, 10, 400, 200);

		contentPane.removeAll(); // limpa tudo da tela
		contentPane.setLayout(null);
		contentPane.add(scroll);
		contentPane.repaint();
		contentPane.revalidate();
	}

	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(44, 24, 346, 210);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Bem-Vindo");
		lblNewLabel.setBounds(144, 11, 62, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("E-MAIL");
		lblNewLabel_1.setBounds(35, 54, 46, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("SENHA");
		lblNewLabel_2.setBounds(35, 93, 46, 14);
		panel.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(75, 51, 215, 20);
		panel.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(75, 90, 215, 20);
		panel.add(passwordField);

		JButton btnNewButton = new JButton("ENTRAR");
		btnNewButton.setBounds(201, 146, 89, 23);
		panel.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
        String email = textField.getText();
        String senha = new String(passwordField.getPassword());

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        UserEntity user = userService.buscarPorEmail(email);

        if (user != null && Criptografia.verificarSenha(senha, user.getPassword())) {
            JOptionPane.showMessageDialog(LoginView.this, "Login bem-sucedido como: " + user.getRole());
            System.out.println("Login bem-sucedido para: " + user.getName() + " com perfil: " + user.getRole());
            
            // Direciona para o menu certo
            if (user.getRole().equals(UserRole.ADMIN)) {
                // Usar a classe utilitária para iniciar o painel de administração
                AdminPanelLauncher.launchAdminPanel(user);
                dispose(); // Fecha a janela de login
            } else if (user.getRole().equals(UserRole.STUDENT)) {
                // new AlunoView(user).setVisible(true);
                System.out.println("Abrir tela Aluno");
                dispose(); // Fecha a janela de login
            } else if (user.getRole().equals(UserRole.AGENT)) {
                // new AgenteView(user).setVisible(true);
                System.out.println("Abrir tela Agente");
                dispose(); // Fecha a janela de login
            }
        } else {
            JOptionPane.showMessageDialog(LoginView.this, "Email ou senha incorretos.");
        }
    }
});

		JButton btnNewButton_1 = new JButton("REGISTRAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistroView registro = new RegistroView();
				JOptionPane.showMessageDialog(LoginView.this, "Direcionando ao login", "Informação",
						JOptionPane.INFORMATION_MESSAGE);
				registro.setVisible(true);
				LoginView.this.dispose();
			}
		});
		btnNewButton_1.setBounds(75, 146, 89, 23);
		panel.add(btnNewButton_1);
	}
}
