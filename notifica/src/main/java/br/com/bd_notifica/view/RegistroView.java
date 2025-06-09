package br.com.bd_notifica.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
// import javax.swing.DefaultComboBoxModel; // Não é necessário se você adiciona itens um por um
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistroView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEmail; // *** ESTA É A VARIÁVEL DE INSTÂNCIA PARA O E-MAIL ***
	private JPasswordField passwordField;
	private JComboBox<String> comboBoxRole; // Variável de instância para o ComboBox

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroView frame = new RegistroView();
					frame.setVisible(true);
					frame.setTitle("Registro");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RegistroView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 324);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(44, 24, 346, 236);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Bem-Vindo");
		lblNewLabel.setBounds(144, 11, 62, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("E-MAIL");
		lblNewLabel_1.setBounds(32, 39, 46, 14);
		panel.add(lblNewLabel_1);

		textFieldEmail = new JTextField(); 
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(72, 36, 215, 20);
		panel.add(textFieldEmail); 
		
		JLabel lblNewLabel_2 = new JLabel("SENHA");
		lblNewLabel_2.setBounds(32, 78, 46, 14);
		panel.add(lblNewLabel_2);

		passwordField = new JPasswordField(); 
		passwordField.setBounds(72, 75, 215, 20);
		panel.add(passwordField);

		JLabel lblNewLabel_3 = new JLabel("ROLE");
		lblNewLabel_3.setBounds(32, 118, 46, 14);
		panel.add(lblNewLabel_3);

		comboBoxRole = new JComboBox<>(); 
		comboBoxRole.addItem("1 - ESTUDANTE");
		comboBoxRole.addItem("2 - GESTOR");
		comboBoxRole.addItem("3 - AGENTE");
		comboBoxRole.setSelectedIndex(-1);
		comboBoxRole.setBounds(72, 114, 215, 22);
		panel.add(comboBoxRole);

		JButton Registrar = new JButton("REGISTRAR");
		Registrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String emailDigitado = textFieldEmail.getText(); 
				String senhaDigitada = new String(passwordField.getPassword());
				String roleSelecionada = (String) comboBoxRole.getSelectedItem();

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

				 if (roleSelecionada == null || roleSelecionada.equals("Selecione uma opção...")) {
				     JOptionPane.showMessageDialog(RegistroView.this, "Por favor, selecione uma ROLE.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
				     comboBoxRole.requestFocusInWindow();
				     return;
				 }

				System.out.println("Botão Clicado para registrar!");
				JOptionPane.showMessageDialog(RegistroView.this, "Registrado com sucesso! Redirecionando...", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

				LoginView loginView = new LoginView();
				loginView.setVisible(true);
				RegistroView.this.dispose();
			}
		});
		Registrar.setBounds(181, 147, 106, 23);
		panel.add(Registrar);

		JLabel lblNewLabel_4 = new JLabel("Possui uma conta?");
		lblNewLabel_4.setBounds(32, 199, 118, 14);
		panel.add(lblNewLabel_4);

		JButton Logar = new JButton("LOGAR");
		Logar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Botão Clicado para ir a página de login pois já tem conta");
				JOptionPane.showMessageDialog(RegistroView.this, "Direcionando a página de Registro", "Informação", JOptionPane.INFORMATION_MESSAGE);
				LoginView loginView = new LoginView();
				loginView.setVisible(true);
				RegistroView.this.dispose();
			}
		});
		Logar.setBounds(124, 195, 89, 23);
		panel.add(Logar);
	}
}