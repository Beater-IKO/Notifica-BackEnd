package br.com.bd_notifica.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.bd_notifica.controllers.UserController;
import br.com.bd_notifica.services.UserService;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.enums.UserRole;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistroView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JComboBox<String> comboBoxRole;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				RegistroView frame = new RegistroView();
				frame.setVisible(true);
				frame.setTitle("Registro");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public RegistroView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				dispose();
				new LoginView().setVisible(true);
			}
		});
		setBounds(100, 100, 450, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(44, 24, 346, 278);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Registro");
		lblNewLabel.setBounds(144, 11, 62, 14);
		panel.add(lblNewLabel);

		// Nome
		JLabel lblNome = new JLabel("NOME");
		lblNome.setBounds(32, 39, 46, 14);
		panel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(72, 36, 215, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);

		// Email
		JLabel lblEmail = new JLabel("E-MAIL");
		lblEmail.setBounds(32, 74, 46, 14);
		panel.add(lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(72, 71, 215, 20);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		// Senha
		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setBounds(32, 113, 46, 14);
		panel.add(lblSenha);

		passwordField = new JPasswordField();
		passwordField.setBounds(72, 110, 215, 20);
		panel.add(passwordField);

		// Role
		JLabel lblRole = new JLabel("ROLE");
		lblRole.setBounds(32, 150, 46, 14);
		panel.add(lblRole);

		comboBoxRole = new JComboBox<>();
		comboBoxRole.addItem("1 - ESTUDANTE");
		comboBoxRole.addItem("2 - GESTOR");
		comboBoxRole.addItem("3 - AGENTE");
		comboBoxRole.setSelectedIndex(-1);
		comboBoxRole.setBounds(72, 146, 215, 22);
		panel.add(comboBoxRole);

		// Botão REGISTRAR
		JButton Registrar = new JButton("REGISTRAR");
		Registrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nomeDigitado = textFieldNome.getText();
				String emailDigitado = textFieldEmail.getText();
				String senhaDigitada = new String(passwordField.getPassword());
				String roleSelecionada = (String) comboBoxRole.getSelectedItem();

				if (nomeDigitado.isBlank()) {
					JOptionPane.showMessageDialog(RegistroView.this, "O campo NOME não pode estar vazio.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
					textFieldNome.requestFocusInWindow();
					return;
				}

				if (emailDigitado.isBlank()) {
					JOptionPane.showMessageDialog(RegistroView.this, "O campo E-MAIL não pode estar vazio.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
					textFieldEmail.requestFocusInWindow();
					return;
				}

				if (!emailDigitado.contains("@")) {
					JOptionPane.showMessageDialog(RegistroView.this, "O e-mail deve conter o caractere '@'.", "Formato Inválido", JOptionPane.WARNING_MESSAGE);
					textFieldEmail.requestFocusInWindow();
					return;
				}

				if (senhaDigitada.isBlank()) {
					JOptionPane.showMessageDialog(RegistroView.this, "O campo SENHA não pode estar vazio.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
					passwordField.requestFocusInWindow();
					return;
				}

				if (roleSelecionada == null) {
					JOptionPane.showMessageDialog(RegistroView.this, "Por favor, selecione uma ROLE.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
					comboBoxRole.requestFocusInWindow();
					return;
				}

				UserRole role = switch (roleSelecionada) {
					case "1 - ESTUDANTE" -> UserRole.STUDENT;
					case "2 - GESTOR" -> UserRole.ADMIN;
					case "3 - AGENTE" -> UserRole.AGENT;
					default -> null;
				};

				if (role == null) {
					JOptionPane.showMessageDialog(RegistroView.this, "Tipo de usuário inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				UserController userController = new UserController(new UserService(new UserRepository()));
				boolean sucesso = userController.registrar(nomeDigitado, emailDigitado, senhaDigitada, role);

				if (sucesso) {
					JOptionPane.showMessageDialog(RegistroView.this, "Registrado com sucesso! Redirecionando...", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					new LoginView().setVisible(true);
					RegistroView.this.dispose();
				} else {
					JOptionPane.showMessageDialog(RegistroView.this, "E-mail já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Registrar.setBounds(181, 179, 106, 23);
		panel.add(Registrar);

		// Já possui conta
		JLabel lblPossuiConta = new JLabel("Possui uma conta?");
		lblPossuiConta.setBounds(32, 224, 118, 14);
		panel.add(lblPossuiConta);

		JButton Logar = new JButton("LOGAR");
		Logar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(RegistroView.this, "Direcionando a página de Login", "Informação", JOptionPane.INFORMATION_MESSAGE);
				LoginView loginView = new LoginView();
				loginView.setVisible(true);
				RegistroView.this.dispose();
			}
		});
		Logar.setBounds(150, 220, 89, 23);
		panel.add(Logar);
	}
}
