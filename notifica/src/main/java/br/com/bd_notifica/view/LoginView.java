package br.com.bd_notifica.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.repositories.UserRepository;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
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
		UserEntity user = new UserEntity();
		List<UserEntity> users = UserRepository.listarTodos();
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("EMAIL");
		model.addColumn("Role");
		
		for(UserEntity ue : users) {
			ue.getEmail();
			ue.getRole();
		}
		
		contentPane.add(model);
		
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
		
		JButton btnNewButton_1 = new JButton("REGISTRAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistroView registro = new RegistroView();
				JOptionPane.showMessageDialog(LoginView.this, "Direcionando ao login", "Informação", JOptionPane.INFORMATION_MESSAGE);
				registro.setVisible(true);
				LoginView.this.dispose();
			}
		});
		btnNewButton_1.setBounds(75, 146, 89, 23);
		panel.add(btnNewButton_1);
	}
}
