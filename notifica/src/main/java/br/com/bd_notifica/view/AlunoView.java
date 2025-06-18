package br.com.bd_notifica.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.UserService;

// Importa o AlunoController
import br.com.bd_notifica.controllers.AlunoController;

public class AlunoView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private AlunoController alunoController; // Instância do AlunoController
    private UserEntity loggedInUser; // Usuário logado

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserRepository tempUserRepository = new UserRepository();
                    UserService tempUserService = new UserService(tempUserRepository);
                    tempUserService.criarUserPadrão(); // Garante que há usuários padrão
                    UserEntity testUser = tempUserRepository.findByEmail("carlos@mail.com"); // Aluno padrão

                    if (testUser == null) {
                        JOptionPane.showMessageDialog(null, "Usuário de teste 'carlos@mail.com' não encontrado. Certifique-se de que o método criarUserPadrão() está sendo executado.", "Erro de Inicialização", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    AlunoView frame = new AlunoView(testUser);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AlunoView(UserEntity loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.alunoController = new AlunoController(loggedInUser); // Instancia o Controller

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 836, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 204, 153));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- PAINEL DO MENU LATERAL ESQUERDO ---
        // Estes botões existem na interface, mas suas ações não serão implementadas neste escopo simplificado.
        JPanel panelMenu = new JPanel();
        panelMenu.setBounds(10, 10, 140, 340);
        contentPane.add(panelMenu);
        panelMenu.setLayout(new GridLayout(0, 1, 0, 10));

        panelMenu.add(new JButton("Criar Ticket"));
        panelMenu.add(new JButton("Ticket em andamento"));
        panelMenu.add(new JButton("Histórico de Tickets"));
        panelMenu.add(new JButton("Listar Tickets Finalizados"));
        panelMenu.add(new JButton("Suporte"));

        // --- PAINEL DO FORMULÁRIO PRINCIPAL (DIREITA) ---
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBounds(154, 10, 658, 340);
        contentPane.add(panelFormulario);
        panelFormulario.setLayout(null);

        JLabel lblTitulo = new JLabel("Escolha as categorias baseado no problema, localização e imagens.");
        lblTitulo.setFont(new Font("Calibri", Font.PLAIN, 19));
        lblTitulo.setBounds(66, 10, 526, 23);
        panelFormulario.add(lblTitulo);

        // --- SEÇÃO 1: SELEÇÃO DE SALAS ---
        JLabel lblSalas = new JLabel("Selecione as salas");
        lblSalas.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblSalas.setBounds(42, 51, 90, 13);
        panelFormulario.add(lblSalas);
        String[] nomeDasSalas = {"Sala 101", "Sala 102", "Sala 103", "Sala 104", "Auditório"};
        JList<String> listaDasSalas = new JList<>(nomeDasSalas);
        listaDasSalas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane painelComRolagem = new JScrollPane(listaDasSalas);
        painelComRolagem.setBounds(42, 70, 122, 50);
        panelFormulario.add(painelComRolagem);

        // --- SEÇÃO 2: ÁREA DA FACULDADE ---
        JLabel lblArea = new JLabel("Área da faculdade");
        lblArea.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblArea.setBounds(197, 51, 101, 13);
        panelFormulario.add(lblArea);
        JRadioButton radioInterno = new JRadioButton("Interno");
        radioInterno.setBounds(256, 70, 70, 21);
        panelFormulario.add(radioInterno);
        JRadioButton radioExterno = new JRadioButton("Externo");
        radioExterno.setBounds(186, 70, 70, 21);
        panelFormulario.add(radioExterno);
        ButtonGroup grupoArea = new ButtonGroup();
        grupoArea.add(radioInterno);
        grupoArea.add(radioExterno);

        // --- SEÇÃO 3: SELEÇÃO DE ANDAR ---
        JLabel lblAndar = new JLabel("Selecione o andar");
        lblAndar.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblAndar.setBounds(347, 51, 104, 13);
        panelFormulario.add(lblAndar);
        JRadioButton Andar1 = new JRadioButton("1º Andar");
        Andar1.setBounds(332, 70, 80, 21);
        panelFormulario.add(Andar1);
        JRadioButton Andar2 = new JRadioButton("2º Andar");
        Andar2.setBounds(413, 70, 80, 21);
        panelFormulario.add(Andar2);
        JRadioButton Andar3 = new JRadioButton("3º Andar");
        Andar3.setBounds(332, 96, 80, 21);
        panelFormulario.add(Andar3);
        JRadioButton Andar4 = new JRadioButton("4º Andar");
        Andar4.setBounds(413, 96, 80, 21);
        panelFormulario.add(Andar4);
        ButtonGroup grupoAndar = new ButtonGroup();
        grupoAndar.add(Andar1);
        grupoAndar.add(Andar2);
        grupoAndar.add(Andar3);
        grupoAndar.add(Andar4);


        // --- SEÇÃO 4: GRAU DE PRIORIDADE ---
        JLabel lblPrioridade = new JLabel("Grau de prioridade");
        lblPrioridade.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblPrioridade.setBounds(512, 51, 110, 13);
        panelFormulario.add(lblPrioridade);
        JRadioButton grauLeve = new JRadioButton("LEVE");
        grauLeve.setBounds(512, 70, 70, 21);
        panelFormulario.add(grauLeve);
        JRadioButton grauMediano = new JRadioButton("MÉDIO");
        grauMediano.setBounds(583, 70, 70, 21);
        panelFormulario.add(grauMediano);
        JRadioButton grauGrave = new JRadioButton("GRAVE");
        grauGrave.setBounds(512, 96, 70, 21);
        panelFormulario.add(grauGrave);
        JRadioButton grauUrgencia = new JRadioButton("URGENTE");
        grauUrgencia.setBounds(583, 96, 80, 21);
        panelFormulario.add(grauUrgencia);
        ButtonGroup grupoPrioridade = new ButtonGroup();
        grupoPrioridade.add(grauLeve);
        grupoPrioridade.add(grauMediano);
        grupoPrioridade.add(grauGrave);
        grupoPrioridade.add(grauUrgencia);

        // --- SEÇÃO 5: PROBLEMA E SUBTIPO (COMBOBOX EM CASCATA) ---
        JLabel lblTipoProblema = new JLabel("Qual o tipo de problema:");
        lblTipoProblema.setBounds(42, 190, 140, 13);
        panelFormulario.add(lblTipoProblema);

        JLabel lblSubtipoProblema = new JLabel("Qual o subtipo:");
        lblSubtipoProblema.setBounds(42, 230, 140, 13);
        panelFormulario.add(lblSubtipoProblema);

        Map<String, String[]> mapaDeProblemas = new HashMap<>();
        mapaDeProblemas.put("Ar Condicionado", new String[]{"Goteira", "Barulho", "Não gela", "Não Liga"});
        mapaDeProblemas.put("Projetor", new String[]{"Lâmpada queimada", "Não liga", "Cabo quebrado"});
        mapaDeProblemas.put("Estrutura", new String[]{"Cadeira quebrada", "Mesa bamba", "Tomada com defeito"});

        JComboBox<String> cbSubtipo = new JComboBox<>();
        cbSubtipo.setBounds(197, 225, 200, 22);
        cbSubtipo.setEnabled(false);
        panelFormulario.add(cbSubtipo);

        JComboBox<String> cbTipo = new JComboBox<>(mapaDeProblemas.keySet().toArray(new String[0]));
        cbTipo.insertItemAt("-- Selecione um Tipo --", 0);
        cbTipo.setSelectedIndex(0);
        cbTipo.setBounds(197, 185, 200, 22);
        panelFormulario.add(cbTipo);

        cbTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoEscolhido = (String) cbTipo.getSelectedItem();
                cbSubtipo.removeAllItems();

                if (tipoEscolhido != null && !tipoEscolhido.equals("-- Selecione um Tipo --")) {
                    String[] subtipos = mapaDeProblemas.get(tipoEscolhido);
                    for (String subtipo : subtipos) {
                        cbSubtipo.addItem(subtipo);
                    }
                    cbSubtipo.setEnabled(true);
                } else {
                    cbSubtipo.setEnabled(false);
                }
            }
        });

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(538, 290, 110, 23);
        panelFormulario.add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedSala = listaDasSalas.getSelectedValue();
                if (selectedSala == null ||
                    grupoArea.getSelection() == null ||
                    grupoAndar.getSelection() == null ||
                    grupoPrioridade.getSelection() == null ||
                    cbTipo.getSelectedIndex() <= 0 ||
                    cbSubtipo.getSelectedItem() == null) {

                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.");
                    return;
                }

                String descricaoProblema = cbTipo.getSelectedItem().toString() + " - " + cbSubtipo.getSelectedItem().toString();
                String sala = selectedSala;

                Area area = null;
                if (radioInterno.isSelected()) {
                    area = Area.INTERNA;
                } else if (radioExterno.isSelected()) {
                    area = Area.EXTERNA;
                }

                Prioridade prioridade = null;
                if (grauLeve.isSelected()) {
                    prioridade = Prioridade.GRAU_LEVE;
                } else if (grauMediano.isSelected()) {
                    prioridade = Prioridade.GRAU_MEDIO;
                } else if (grauGrave.isSelected()) {
                    prioridade = Prioridade.GRAU_ALTO;
                } else if (grauUrgencia.isSelected()) {
                    prioridade = Prioridade.GRAU_URGENTE;
                }

                String andarSelecionado = "";
                if (Andar1.isSelected()) andarSelecionado = "1º Andar";
                else if (Andar2.isSelected()) andarSelecionado = "2º Andar";
                else if (Andar3.isSelected()) andarSelecionado = "3º Andar";
                else if (Andar4.isSelected()) andarSelecionado = "4º Andar";

                String descricaoFinal = "Local: " + sala + ", " + andarSelecionado + ". Problema: " + descricaoProblema;

                // Chama o método criarTicket do AlunoController para processar e salvar
                boolean sucesso = alunoController.criarTicket(descricaoFinal, sala, area, prioridade);

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Ticket criado com sucesso!");
                    limparFormulario(listaDasSalas, grupoArea, grupoAndar, grupoPrioridade, cbTipo, cbSubtipo);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar ticket. Verifique o console para detalhes.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void limparFormulario(JList<String> listaDasSalas, ButtonGroup grupoArea, ButtonGroup grupoAndar, ButtonGroup grupoPrioridade, JComboBox<String> cbTipo, JComboBox<String> cbSubtipo) {
        listaDasSalas.clearSelection();
        grupoArea.clearSelection();
        grupoAndar.clearSelection();
        grupoPrioridade.clearSelection();
        cbTipo.setSelectedIndex(0);
        cbSubtipo.removeAllItems();
        cbSubtipo.setEnabled(false);
    }
}