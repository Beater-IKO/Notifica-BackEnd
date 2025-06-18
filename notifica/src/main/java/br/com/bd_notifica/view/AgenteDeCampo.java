package br.com.bd_notifica.view;

import javax.swing.*;
import java.awt.*;

public class AgenteDeCampo extends JFrame {
    // Variáveis para os componentes que precisam ser atualizados
    private JLabel titulo;
    private JLabel labelSala;
    private JLabel labelAndar;
    private JLabel labelArea;
    private JLabel labelPrioridade;
    private JTextArea areaDescricao;

    public AgenteDeCampo() {
        setTitle("Chamado de Urgência");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Painel lateral
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(3, 1, 10, 10));
        painelLateral.setBounds(10, 10, 250, 640);

        JButton btnVisualizar = new JButton("Visualizar chamados");
        JButton btnHistorico = new JButton("Historico de chamados");
        JButton btnProtocolos = new JButton("Protocolos");

        painelLateral.add(btnVisualizar);
        painelLateral.add(btnHistorico);
        painelLateral.add(btnProtocolos);

        add(painelLateral);

        // Painel da lista de chamados
        JPanel painelChamados = new JPanel();
        painelChamados.setLayout(new BoxLayout(painelChamados, BoxLayout.Y_AXIS));
        painelChamados.setBounds(270, 10, 200, 640);

        // Dados para os diferentes chamados
        String[] problemas = {
                "Piso quebrado",
                "Ar condicionado",
                "Lâmpada queimada",
                "Vazamento água",
                "Cadeira quebrada",
                "Projetor com defeito"
        };

        String[] areas = { "Interno", "Externo" };
        String[] andares = { "1º Andar", "2º Andar", "3º Andar", "4º Andar" };
        String[] gravidades = { "Leve", "Mediana", "Grave", "Urgencia" };
        int[] salas = { 210, 225, 247, 268, 305, 342 };

        // Cores para as diferentes gravidades
        Color[] coresGravidade = {
                new Color(144, 238, 144), // Verde claro para Leve
                new Color(255, 255, 0), // Amarelo para Mediana
                new Color(255, 165, 0), // Laranja para Grave
                new Color(255, 0, 0) // Vermelho para Urgência
        };

        for (int i = 0; i < 6; i++) {
            // Adiciona um pequeno espaço entre os painéis
            if (i > 0) {
                painelChamados.add(Box.createRigidArea(new Dimension(0, 5)));
            }

            // Determina aleatoriamente a gravidade para este chamado
            int indiceGravidade = i % 4; // Distribui as gravidades
            String gravidade = gravidades[indiceGravidade];

            // Determina a área (interno/externo)
            String area = areas[i % 2];

            // Determina o andar
            String andar = andares[i % 4];

            // Determina a sala
            int sala = salas[i];

            JPanel chamado = new JPanel();
            chamado.setLayout(new BorderLayout());
            chamado.setBackground(coresGravidade[indiceGravidade]);
            chamado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            chamado.setMaximumSize(new Dimension(180, 80));

            JLabel label = new JLabel(
                    "<html>Usuario " + (i + 1) + " - " + (10000 + i) +
                            "<br>" + problemas[i] +
                            "<br>" + area +
                            "<br>Sala " + sala + "</html>");
            label.setVerticalAlignment(SwingConstants.TOP);

            JButton btnGravidade = new JButton(gravidade.toUpperCase());
            btnGravidade.setBackground(coresGravidade[indiceGravidade]);
            btnGravidade.setForeground(indiceGravidade >= 2 ? Color.WHITE : Color.BLACK);
            btnGravidade.setFocusable(false);

            chamado.add(label, BorderLayout.CENTER);
            chamado.add(btnGravidade, BorderLayout.EAST);
            painelChamados.add(chamado);

            // Adiciona ação ao clicar no chamado
            final int index = i;
            chamado.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    atualizarPainelDetalhes(problemas[index], sala, andar, area, gravidade);
                }
            });

            // Adiciona ação ao clicar no botão de gravidade também
            final int finalI = i;
            btnGravidade.addActionListener(e -> {
                atualizarPainelDetalhes(problemas[finalI], sala, andar, area, gravidade);
            });
        }

        add(painelChamados);

        // Painel de detalhes do chamado
        JPanel painelDetalhes = new JPanel();
        painelDetalhes.setLayout(null);
        painelDetalhes.setBounds(480, 10, 690, 640);
        painelDetalhes.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        titulo = new JLabel("PISO QUEBRADO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.MAGENTA);
        titulo.setBounds(250, 10, 300, 30);
        painelDetalhes.add(titulo);

        labelSala = new JLabel("Sala 310");
        labelSala.setBounds(30, 50, 100, 30);
        labelSala.setOpaque(true);
        labelSala.setBackground(Color.LIGHT_GRAY);
        painelDetalhes.add(labelSala);

        labelAndar = new JLabel("2º Andar");
        labelAndar.setBounds(150, 50, 100, 30);
        labelAndar.setOpaque(true);
        labelAndar.setBackground(Color.ORANGE);
        painelDetalhes.add(labelAndar);

        labelArea = new JLabel("Interno");
        labelArea.setBounds(270, 50, 100, 30);
        labelArea.setOpaque(true);
        labelArea.setBackground(Color.YELLOW);
        painelDetalhes.add(labelArea);

        labelPrioridade = new JLabel("Urgencia");
        labelPrioridade.setBounds(390, 50, 100, 30);
        labelPrioridade.setOpaque(true);
        labelPrioridade.setBackground(Color.RED);
        labelPrioridade.setForeground(Color.WHITE);
        painelDetalhes.add(labelPrioridade);

        areaDescricao = new JTextArea(
                "O piso da sala de aula apresenta várias fissuras e buracos que podem representar riscos para os alunos. É necessário realizar o reparo com urgência para garantir a segurança e o bem-estar dos estudantes.");
        areaDescricao.setBounds(30, 100, 630, 200);
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setEditable(false);
        areaDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        areaDescricao.setForeground(Color.MAGENTA);
        areaDescricao.setBorder(BorderFactory.createEtchedBorder());
        painelDetalhes.add(areaDescricao);

        JLabel imagem = new JLabel();
        imagem.setBounds(30, 310, 150, 150);
        imagem.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painelDetalhes.add(imagem);

        // Checkboxes para status do chamado
        JCheckBox chkCiente = new JCheckBox("Estou ciente");
        JCheckBox chkFazer = new JCheckBox("A Fazer");
        JCheckBox chkFinalizado = new JCheckBox("Finalizado");

        // Configuração visual dos checkboxes
        chkCiente.setFont(new Font("Calibri", Font.PLAIN, 20));
        chkFazer.setFont(new Font("Calibri", Font.PLAIN, 20));
        chkFinalizado.setFont(new Font("Calibri", Font.PLAIN, 20));

        chkCiente.setBounds(100, 500, 140, 40);
        chkFazer.setBounds(270, 500, 140, 40);
        chkFinalizado.setBounds(440, 500, 140, 40);

        // Criando um ButtonGroup para garantir que apenas um checkbox seja selecionado
        ButtonGroup grupoStatus = new ButtonGroup();
        grupoStatus.add(chkCiente);
        grupoStatus.add(chkFazer);
        grupoStatus.add(chkFinalizado);

        painelDetalhes.add(chkCiente);
        painelDetalhes.add(chkFazer);
        painelDetalhes.add(chkFinalizado);

        // Botão para salvar o status selecionado
        JButton btnSalvar = new JButton("Salvar Status");
        btnSalvar.setFont(new Font("Calibri", Font.BOLD, 16));
        btnSalvar.setBounds(270, 560, 150, 40);
        btnSalvar.setBackground(new Color(0, 153, 0));
        btnSalvar.setForeground(Color.WHITE);

        // Adiciona ação ao botão salvar
        btnSalvar.addActionListener(e -> {
            String status = "";
            if (chkCiente.isSelected()) {
                status = "Estou ciente";
            } else if (chkFazer.isSelected()) {
                status = "A Fazer";
            } else if (chkFinalizado.isSelected()) {
                status = "Finalizado";
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um status!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Status alterado para: " + status, "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        painelDetalhes.add(btnSalvar);

        add(painelDetalhes);

        setVisible(true);
    }

    // Método para atualizar o painel de detalhes com base no chamado selecionado
    private void atualizarPainelDetalhes(String problema, int sala, String andar, String area, String gravidade) {
        // Atualiza o título
        titulo.setText(problema.toUpperCase());

        // Atualiza as informações
        labelSala.setText("Sala " + sala);
        labelAndar.setText(andar);
        labelArea.setText(area);
        labelPrioridade.setText(gravidade);

        // Define a cor da prioridade
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

        // Atualiza a descrição com base no problema
        String descricaoTexto = "";
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
        }
        areaDescricao.setText(descricaoTexto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgenteDeCampo::new);
    }
}