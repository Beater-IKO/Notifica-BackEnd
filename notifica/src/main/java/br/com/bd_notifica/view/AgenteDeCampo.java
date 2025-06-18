package br.com.bd_notifica.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AgenteDeCampo extends JFrame {
    private JLabel titulo;
    private JLabel labelSala;
    private JLabel labelAndar;
    private JLabel labelArea;
    private JLabel labelPrioridade;
    private JTextArea areaDescricao;

    // Mapa para armazenar o status de cada ticket (chave: identificador único do
    // ticket, valor: status)
    private Map<String, String> statusTickets = new HashMap<>();

    // Variáveis para controlar o ticket atual
    private String ticketAtualId;
    private JCheckBox chkCiente;
    private JCheckBox chkFazer;
    private JCheckBox chkFinalizado;

    public AgenteDeCampo() {
        setTitle("Chamado de Urgência");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(3, 1, 10, 10));
        painelLateral.setBounds(10, 10, 250, 640);

        JButton btnVisualizar = criarBotaoMenu("Visualizar chamados");
        JButton btnHistorico = criarBotaoMenu("Histórico de chamados");
        JButton btnProtocolos = criarBotaoMenu("Protocolos");

        painelLateral.add(btnVisualizar);
        painelLateral.add(btnHistorico);
        painelLateral.add(btnProtocolos);

        add(painelLateral);

        JPanel painelChamados = new JPanel();
        painelChamados.setLayout(new BoxLayout(painelChamados, BoxLayout.Y_AXIS));
        painelChamados.setBounds(270, 10, 200, 640);

        String[] problemas = { "Piso quebrado", "Ar condicionado", "Lâmpada queimada", "Vazamento água",
                "Cadeira quebrada", "Projetor com defeito" };
        String[] areas = { "Interno", "Externo" };
        String[] andares = { "1º Andar", "2º Andar", "3º Andar", "4º Andar" };
        String[] gravidades = { "Leve", "Mediana", "Grave", "Urgencia" };
        int[] salas = { 210, 225, 247, 268, 305, 342 };

        Color[] coresGravidade = {
                new Color(144, 238, 144),
                new Color(255, 255, 0),
                new Color(255, 165, 0),
                new Color(255, 0, 0)
        };

        for (int i = 0; i < 6; i++) {
            if (i > 0)
                painelChamados.add(Box.createRigidArea(new Dimension(0, 5)));

            int indiceGravidade = i % 4;
            String gravidade = gravidades[indiceGravidade];
            String area = areas[i % 2];
            String andar = andares[i % 4];
            int sala = salas[i];

            JPanel chamado = new JPanel();
            chamado.setLayout(new BorderLayout());
            chamado.setBackground(coresGravidade[indiceGravidade]);
            chamado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            chamado.setMaximumSize(new Dimension(180, 80));

            JLabel label = new JLabel("<html>Usuario " + (i + 1) + " - " + (10000 + i) +
                    "<br>" + problemas[i] + "<br>" + area + "<br>Sala " + sala + "</html>");
            label.setVerticalAlignment(SwingConstants.TOP);

            JButton btnGravidade = new JButton(gravidade.toUpperCase());
            btnGravidade.setBackground(coresGravidade[indiceGravidade]);
            btnGravidade.setForeground(indiceGravidade >= 2 ? Color.WHITE : Color.BLACK);
            btnGravidade.setFocusable(false);

            chamado.add(label, BorderLayout.CENTER);
            chamado.add(btnGravidade, BorderLayout.EAST);
            painelChamados.add(chamado);

            final int index = i;
            // Criamos um ID único para cada ticket baseado no problema e sala
            final String ticketId = problemas[i] + "_" + sala;

            chamado.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    atualizarPainelDetalhes(problemas[index], sala, andar, area, gravidade, ticketId);
                }
            });
            btnGravidade.addActionListener(
                    e -> atualizarPainelDetalhes(problemas[index], sala, andar, area, gravidade, ticketId));
        }

        add(painelChamados);

        JPanel painelDetalhes = new JPanel();
        painelDetalhes.setLayout(null);
        painelDetalhes.setBounds(480, 10, 690, 640);
        painelDetalhes.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        titulo = new JLabel("PISO QUEBRADO", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(180, 0, 180));
        titulo.setBounds(200, 10, 290, 30);
        painelDetalhes.add(titulo);

        labelSala = criarTag("Sala 310", Color.LIGHT_GRAY);
        labelSala.setBounds(30, 50, 100, 30);
        painelDetalhes.add(labelSala);

        labelAndar = criarTag("2º Andar", Color.ORANGE);
        labelAndar.setBounds(150, 50, 100, 30);
        painelDetalhes.add(labelAndar);

        labelArea = criarTag("Interno", Color.YELLOW);
        labelArea.setBounds(270, 50, 100, 30);
        painelDetalhes.add(labelArea);

        labelPrioridade = criarTag("Urgencia", Color.RED);
        labelPrioridade.setBounds(390, 50, 100, 30);
        labelPrioridade.setForeground(Color.WHITE);
        painelDetalhes.add(labelPrioridade);

        areaDescricao = new JTextArea();
        areaDescricao.setBounds(30, 100, 630, 200);
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setEditable(false);
        areaDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaDescricao.setForeground(new Color(130, 0, 130));
        areaDescricao.setBackground(new Color(255, 255, 255));
        areaDescricao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        painelDetalhes.add(areaDescricao);

        JLabel imagem = new JLabel("[Imagem]");
        imagem.setBounds(30, 310, 180, 120);
        imagem.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painelDetalhes.add(imagem);

        chkCiente = new JCheckBox("Estou ciente");
        chkFazer = new JCheckBox("A Fazer");
        chkFinalizado = new JCheckBox("Finalizado");

        chkCiente.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        chkFazer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        chkFinalizado.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        chkCiente.setBounds(100, 500, 140, 40);
        chkFazer.setBounds(270, 500, 140, 40);
        chkFinalizado.setBounds(440, 500, 140, 40);

        ButtonGroup grupoStatus = new ButtonGroup();
        grupoStatus.add(chkCiente);
        grupoStatus.add(chkFazer);
        grupoStatus.add(chkFinalizado);

        painelDetalhes.add(chkCiente);
        painelDetalhes.add(chkFazer);
        painelDetalhes.add(chkFinalizado);

        JButton btnSalvar = new JButton("Salvar Status");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSalvar.setBounds(270, 560, 150, 40);
        btnSalvar.setBackground(new Color(0, 153, 0));
        btnSalvar.setForeground(Color.WHITE);

        btnSalvar.addActionListener(e -> {
            String status = "";
            if (chkCiente.isSelected())
                status = "Estou ciente";
            else if (chkFazer.isSelected())
                status = "A Fazer";
            else if (chkFinalizado.isSelected())
                status = "Finalizado";
            else {
                JOptionPane.showMessageDialog(this, "Selecione um status!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Salva o status do ticket atual no mapa
            if (ticketAtualId != null) {
                statusTickets.put(ticketAtualId, status);
                JOptionPane.showMessageDialog(this, "Status alterado para: " + status, "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        painelDetalhes.add(btnSalvar);
        add(painelDetalhes);

        // Inicializa com o primeiro ticket
        atualizarPainelDetalhes(problemas[0], salas[0], andares[0], areas[0], gravidades[0],
                problemas[0] + "_" + salas[0]);

        setVisible(true);
    }

    private JLabel criarTag(String texto, Color bg) {
        JLabel tag = new JLabel(" " + texto + " ");
        tag.setOpaque(true);
        tag.setBackground(bg);
        tag.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tag.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return tag;
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return btn;
    }

    private void atualizarPainelDetalhes(String problema, int sala, String andar, String area, String gravidade,
            String ticketId) {
        // Salva o ID do ticket atual
        ticketAtualId = ticketId;

        titulo.setText(problema.toUpperCase());
        labelSala.setText("Sala " + sala);
        labelAndar.setText(andar);
        labelArea.setText(area);
        labelPrioridade.setText(gravidade);

        switch (gravidade) {
            case "Leve":
                labelPrioridade.setBackground(new Color(144, 238, 144));
                labelPrioridade.setForeground(Color.BLACK);
                break;
            case "Mediana":
                labelPrioridade.setBackground(Color.YELLOW);
                labelPrioridade.setForeground(Color.BLACK);
                break;
            case "Grave":
                labelPrioridade.setBackground(Color.ORANGE);
                labelPrioridade.setForeground(Color.BLACK);
                break;
            case "Urgencia":
                labelPrioridade.setBackground(Color.RED);
                labelPrioridade.setForeground(Color.WHITE);
                break;
        }

        String descricaoTexto;
        switch (problema) {
            case "Piso quebrado":
                descricaoTexto = "O piso da sala de aula apresenta várias fissuras e buracos que podem representar riscos para os alunos. É necessário realizar o reparo com urgência para garantir a segurança e o bem-estar dos estudantes.";
                break;
            case "Ar condicionado":
                descricaoTexto = "O ar condicionado da sala está com problemas, emitindo ruídos altos e não refrigerando adequadamente. A temperatura elevada está prejudicando o ambiente de aprendizagem dos alunos.";
                break;
            case "Lâmpada queimada":
                descricaoTexto = "Algumas lâmpadas da sala estão queimadas, deixando o ambiente com iluminação insuficiente. Isso dificulta a leitura e visualização do quadro pelos alunos.";
                break;
            case "Vazamento água":
                descricaoTexto = "Há um vazamento de água no teto da sala, formando poças no chão. Além do desperdício de água, há risco de acidentes por escorregões e danos aos equipamentos eletrônicos.";
                break;
            case "Cadeira quebrada":
                descricaoTexto = "Várias cadeiras da sala estão com problemas estruturais, algumas com encostos quebrados e outras com pernas instáveis. Os alunos estão tendo dificuldade para se acomodar adequadamente.";
                break;
            case "Projetor com defeito":
                descricaoTexto = "O projetor da sala está apresentando falhas na imagem, com cores distorcidas e desligamentos repentinos. Isso está prejudicando as apresentações e o andamento das aulas.";
                break;
            default:
                descricaoTexto = "";
        }
        areaDescricao.setText(descricaoTexto);

        // Limpa a seleção atual
        chkCiente.setSelected(false);
        chkFazer.setSelected(false);
        chkFinalizado.setSelected(false);

        // Verifica se este ticket já tem um status salvo
        if (statusTickets.containsKey(ticketId)) {
            String statusSalvo = statusTickets.get(ticketId);
            switch (statusSalvo) {
                case "Estou ciente":
                    chkCiente.setSelected(true);
                    break;
                case "A Fazer":
                    chkFazer.setSelected(true);
                    break;
                case "Finalizado":
                    chkFinalizado.setSelected(true);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgenteDeCampo::new);
    }
}