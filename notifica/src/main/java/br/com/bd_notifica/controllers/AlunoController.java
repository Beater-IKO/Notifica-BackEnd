// Define que esta classe pertence ao pacote 'controllers' dentro da estrutura 'br.com.bd_notifica'
package br.com.bd_notifica.controllers;

// Importa todas as classes necessárias do Swing e do AWT para a interface gráfica e eventos
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List; // Para usar a interface List (ex: na JList)
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane; // Para adicionar barras de rolagem
import javax.swing.ButtonGroup;   // Para agrupar botões de rádio
import javax.swing.JComboBox;   // Para caixas de seleção (dropdown)
import javax.swing.JList;       // Para listas de seleção
import javax.swing.JOptionPane; // Para mostrar caixas de diálogo (pop-ups)
import javax.swing.ListSelectionModel; // Para definir como os itens da lista podem ser selecionados
import java.util.HashMap; // Para criar a estrutura de dados chave-valor
import java.util.Map;     // Para usar a interface Map (boa prática)

// A classe principal da sua janela, que herda todas as funcionalidades de um JFrame
public class AlunoController extends JFrame {

    // Identificador de versão padrão para classes serializáveis (usado pelo Java para controle interno)
    private static final long serialVersionUID = 1L;
    // O painel principal que conterá todos os outros componentes
    private JPanel contentPane;

    /**
     * O método principal que inicia a aplicação.
     */
    public static void main(String[] args) {
        // EventQueue.invokeLater garante que a criação da interface gráfica seja feita na thread correta (Event Dispatch Thread - EDT)
        // Isso previne problemas de concorrência e garante que a UI seja segura.
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Cria uma nova instância da nossa janela
                    AlunoController frame = new AlunoController();
                    // Torna a janela visível na tela
                    frame.setVisible(true);
                } catch (Exception e) {
                    // Se algum erro acontecer durante a criação, ele será impresso no console
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * O construtor da classe. É aqui que toda a interface é montada.
     */
    public AlunoController() {
        // --- CONFIGURAÇÕES GERAIS DA JANELA ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define que o programa fechará quando a janela for fechada
        setBounds(100, 100, 836, 400); // Define a posição (x, y) e o tamanho (largura, altura) da janela
        contentPane = new JPanel(); // Cria o painel principal
        contentPane.setBackground(new Color(153, 204, 153)); // Define a cor de fundo do painel
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Adiciona uma margem interna
        setContentPane(contentPane); // Define este como o painel principal da janela
        contentPane.setLayout(null); // Usa um layout nulo (manual), onde definimos a posição exata de cada componente

        // --- PAINEL DO MENU LATERAL ESQUERDO ---
        JPanel panelMenu = new JPanel();
        panelMenu.setBounds(10, 10, 140, 340); // Posição e tamanho do painel do menu
        contentPane.add(panelMenu); // Adiciona o painel do menu ao painel principal
        panelMenu.setLayout(new GridLayout(0, 1, 0, 10)); // Define um layout de grade para organizar os botões verticalmente com espaçamento

        // Adiciona os botões de navegação ao menu
        panelMenu.add(new JButton("Criar Ticket"));
        panelMenu.add(new JButton("Ticket em andamento"));
        panelMenu.add(new JButton("Histórico de Tickets"));
        panelMenu.add(new JButton("Listar Tickets Finalizados"));
        panelMenu.add(new JButton("Suporte"));

        // --- PAINEL DO FORMULÁRIO PRINCIPAL (DIREITA) ---
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBounds(154, 10, 658, 340); // Posição e tamanho do painel do formulário
        contentPane.add(panelFormulario); // Adiciona ao painel principal
        panelFormulario.setLayout(null); // Também usa layout manual

        // Título do formulário
        JLabel lblTitulo = new JLabel("Escolha as categorias baseado no problema, localização e imagens.");
        lblTitulo.setFont(new Font("Calibri", Font.PLAIN, 19));
        lblTitulo.setBounds(66, 10, 526, 23);
        panelFormulario.add(lblTitulo);

        // --- SEÇÃO 1: SELEÇÃO DE SALAS ---
        JLabel lblSalas = new JLabel("Selecione as salas");
        lblSalas.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblSalas.setBounds(42, 51, 90, 13);
        panelFormulario.add(lblSalas);
        String[] nomeDasSalas = {"Sala 101", "Sala 102", "Sala 103", "Sala 104", "Auditório"}; // Os dados da lista
        JList<String> listaDasSalas = new JList<>(nomeDasSalas); // Cria a JList com os dados
        listaDasSalas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Permite selecionar vários itens com Ctrl ou Shift
        JScrollPane painelComRolagem = new JScrollPane(listaDasSalas); // Coloca a JList dentro de um painel de rolagem
        painelComRolagem.setBounds(42, 70, 122, 50); // Define a posição e o tamanho do painel de rolagem
        panelFormulario.add(painelComRolagem); // Adiciona o painel de rolagem (com a lista dentro) ao formulário

        // --- SEÇÃO 2: ÁREA DA FACULDADE ---
        JLabel lblArea = new JLabel("Área da faculdade");
        // ... (código dos componentes da área)
        lblArea.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblArea.setBounds(197, 51, 101, 13);
        panelFormulario.add(lblArea);
        JRadioButton radioInterno = new JRadioButton("Interno");
        radioInterno.setBounds(256, 70, 70, 21);
        panelFormulario.add(radioInterno);
        JRadioButton radioExterno = new JRadioButton("Externo");
        radioExterno.setBounds(186, 70, 70, 21);
        panelFormulario.add(radioExterno);
        ButtonGroup grupoArea = new ButtonGroup(); // Agrupa os botões de rádio para que apenas um possa ser selecionado
        grupoArea.add(radioInterno);
        grupoArea.add(radioExterno);

        // --- SEÇÃO 3: SELEÇÃO DE ANDAR ---
        // ... (código dos componentes de andar)
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
        // ... (código dos componentes de prioridade)
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

        // Cria a estrutura de dados (Mapa) para armazenar a relação entre tipo e subtipos
        Map<String, String[]> mapaDeProblemas = new HashMap<>();
        mapaDeProblemas.put("Ar Condicionado", new String[]{"Goteira", "Barulho", "Não gela", "Não Liga"});
        mapaDeProblemas.put("Projetor", new String[]{"Lâmpada queimada", "Não liga", "Cabo quebrado"});
        mapaDeProblemas.put("Estrutura", new String[]{"Cadeira quebrada", "Mesa bamba", "Tomada com defeito"});

        // Cria a ComboBox de subtipos PRIMEIRO, para que a de tipos fique visualmente na frente
        JComboBox<String> cbSubtipo = new JComboBox<>();
        cbSubtipo.setBounds(197, 225, 200, 22);
        cbSubtipo.setEnabled(false); // Começa desabilitada
        panelFormulario.add(cbSubtipo);

        // Cria a ComboBox de tipos, preenchendo-a com as chaves do mapa
        JComboBox<String> cbTipo = new JComboBox<>(mapaDeProblemas.keySet().toArray(new String[0]));
        cbTipo.insertItemAt("-- Selecione um Tipo --", 0); // Adiciona uma instrução inicial na primeira posição
        cbTipo.setSelectedIndex(0); // Garante que a instrução inicial seja a selecionada
        cbTipo.setBounds(197, 185, 200, 22);
        panelFormulario.add(cbTipo);

        // Adiciona o "ouvinte" de eventos à ComboBox de tipos. Este código será executado toda vez que o usuário selecionar um item.
        cbTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Pega o item que o usuário selecionou
                String tipoEscolhido = (String) cbTipo.getSelectedItem();
                // Limpa a ComboBox de subtipos para remover opções antigas
                cbSubtipo.removeAllItems();

                // Verifica se o usuário escolheu uma categoria válida (e não a instrução inicial)
                if (tipoEscolhido != null && !tipoEscolhido.equals("-- Selecione um Tipo --")) {
                    // Busca no mapa a lista de subtipos correspondente à chave (tipo) escolhida
                    String[] subtipos = mapaDeProblemas.get(tipoEscolhido);
                    // Adiciona cada subtipo encontrado à segunda ComboBox
                    for (String subtipo : subtipos) {
                        cbSubtipo.addItem(subtipo);
                    }
                    // Habilita a ComboBox de subtipos para o usuário poder interagir
                    cbSubtipo.setEnabled(true);
                } else {
                    // Se o usuário voltou para a opção "-- Selecione --", desabilita a ComboBox de subtipos
                    cbSubtipo.setEnabled(false);
                }
            }
        });

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(538, 290, 110, 23);
        panelFormulario.add(btnSalvar);

        // Adiciona o "ouvinte" ao botão Salvar. Este código será executado quando o botão for clicado.
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Faz a validação de todos os campos obrigatórios em uma única condição 'if'
                if (listaDasSalas.getSelectedValuesList().isEmpty() || // Verifica se nenhuma sala foi selecionada
                    grupoArea.getSelection() == null ||                 // Verifica se nenhuma área foi selecionada
                    grupoAndar.getSelection() == null ||                // Verifica se nenhum andar foi selecionado
                    grupoPrioridade.getSelection() == null ||           // Verifica se nenhuma prioridade foi selecionada
                    cbTipo.getSelectedIndex() <= 0 ||                   // Verifica se o tipo de problema não foi selecionado (índice 0 é a instrução)
                    cbSubtipo.getSelectedItem() == null) {              // Verifica se o subtipo não foi selecionado
                    
                    // Se qualquer uma das condições for verdadeira, mostra uma mensagem de erro
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.");
                    return; // Interrompe a execução do método para não salvar dados incompletos
                }
                
                // Se todas as validações passaram, mostra uma mensagem de sucesso
                JOptionPane.showMessageDialog(null, "Ticket criado com sucesso!");
                // Aqui entraria a lógica para pegar todos os dados e enviar para o banco de dados.
            }
        });
    }


}