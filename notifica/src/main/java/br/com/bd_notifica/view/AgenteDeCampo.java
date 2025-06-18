package br.com.bd_notifica.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.TicketService;

public class AgenteDeCampo extends JFrame {
    private JLabel titulo;
    private JLabel labelSala;
    private JLabel labelAndar;
    private JLabel labelArea;
    private JLabel labelPrioridade;
    private JTextArea areaDescricao;

    // Serviço para interagir com os tickets no banco de dados
    private TicketService ticketService;
    
    // Lista de tickets carregados do banco de dados
    private List<Ticket> tickets;
    
    // Mapa para armazenar o status de cada ticket (chave: identificador único do
    // ticket, valor: status)
    private Map<String, String> statusTickets = new HashMap<>();

    // Variáveis para controlar o ticket atual
    private Long ticketAtualId;
    private JCheckBox chkCiente;
    private JCheckBox chkFazer;
    private JCheckBox chkFinalizado;

    public AgenteDeCampo() {
        try {
            // Inicializa o serviço de tickets
            ticketService = new TicketService(new TicketRepository());
            
            // Carrega os tickets do banco de dados
            tickets = ticketService.listarTodos();
            
            // Inicializa o mapa de status com os valores do banco de dados
            if (tickets != null) {
                for (Ticket ticket : tickets) {
                    if (ticket != null && ticket.getDescricao() != null && ticket.getSala() != null) {
                        statusTickets.put(ticket.getDescricao() + "_" + ticket.getSala(), ticket.getStatus());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tickets = null;
        }
        
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

        // Verifica se existem tickets no banco de dados
        if (tickets != null && !tickets.isEmpty()) {
            // Usa os tickets do banco de dados
            for (int i = 0; i < Math.min(tickets.size(), 6); i++) {
                if (i > 0)
                    painelChamados.add(Box.createRigidArea(new Dimension(0, 5)));
                
                Ticket ticket = tickets.get(i);
                
                // Determina o índice de gravidade com base na prioridade do ticket
                int indiceGravidade = 0;
                if (ticket.getPrioridade() != null) {
                    if (ticket.getPrioridade() == Prioridade.GRAU_LEVE) indiceGravidade = 0;
                    else if (ticket.getPrioridade() == Prioridade.GRAU_MEDIO) indiceGravidade = 1;
                    else if (ticket.getPrioridade() == Prioridade.GRAU_ALTO) indiceGravidade = 2;
                    else if (ticket.getPrioridade() == Prioridade.GRAU_URGENTE) indiceGravidade = 3;
                }
                
                String gravidade = gravidades[indiceGravidade];
                String area = (ticket.getArea() != null && ticket.getArea() == Area.INTERNA) ? areas[0] : areas[1];
                // Determina o andar com base na sala
                String andar = andares[i % 4]; // Valor padrão
                
                // Tenta extrair o andar da sala (assumindo formato como "Sala 205" onde 2 é o andar)
                String sala = ticket.getSala();
                if (sala != null && sala.length() >= 5) {
                    try {
                        char primeiroDigito = sala.charAt(4);
                        if (Character.isDigit(primeiroDigito)) {
                            int numAndar = Character.getNumericValue(primeiroDigito);
                            andar = numAndar + "º Andar";
                        }
                    } catch (Exception e) {
                        // Mantém o valor padrão em caso de erro
                    }
                }
                
                JPanel chamado = new JPanel();
                chamado.setLayout(new BorderLayout());
                chamado.setBackground(coresGravidade[indiceGravidade]);
                chamado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                chamado.setMaximumSize(new Dimension(180, 80));
                
                String userName = ticket.getUser() != null ? ticket.getUser().getName() : "Usuário " + (i + 1);
                String userId = ticket.getUser() != null ? String.valueOf(ticket.getUser().getId()) : String.valueOf(10000 + i);
                
                JLabel label = new JLabel("<html>" + userName + " - " + userId +
                        "<br>" + ticket.getDescricao() + "<br>" + area + "<br>" + sala + "</html>");
                label.setVerticalAlignment(SwingConstants.TOP);
                
                JButton btnGravidade = new JButton(gravidade.toUpperCase());
                btnGravidade.setBackground(coresGravidade[indiceGravidade]);
                btnGravidade.setForeground(indiceGravidade >= 2 ? Color.WHITE : Color.BLACK);
                btnGravidade.setFocusable(false);
                
                chamado.add(label, BorderLayout.CENTER);
                chamado.add(btnGravidade, BorderLayout.EAST);
                painelChamados.add(chamado);
                
                final Ticket currentTicket = ticket;
                final String currentAndar = andar; // Captura o valor atual do andar
                final int index = i;
                
                chamado.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        atualizarPainelDetalhesComTicket(currentTicket, currentAndar);
                    }
                });
                btnGravidade.addActionListener(e -> atualizarPainelDetalhesComTicket(currentTicket, currentAndar));
            }
        } else {
            // Usa os dados estáticos como fallback
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
    
                final String currentAndar = andar; // Captura o valor atual do andar
                
                chamado.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        atualizarPainelDetalhes(problemas[index], sala, currentAndar, area, gravidade, ticketId);
                    }
                });
                btnGravidade.addActionListener(
                        e -> atualizarPainelDetalhes(problemas[index], sala, currentAndar, area, gravidade, ticketId));
            }
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

            // Salva o status do ticket atual no banco de dados
            if (ticketAtualId != null) {
                try {
                    // Busca o ticket pelo ID
                    Ticket ticket = ticketService.buscarPorId(ticketAtualId);
                    if (ticket != null) {
                        // Atualiza o status
                        ticket.setStatus(status);
                        // Salva no banco de dados
                        ticketService.editar(ticket);
                        // Atualiza o mapa local
                        if (ticket.getDescricao() != null && ticket.getSala() != null) {
                            statusTickets.put(ticket.getDescricao() + "_" + ticket.getSala(), status);
                        }
                        
                        JOptionPane.showMessageDialog(this, "Status alterado para: " + status, "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Ticket não encontrado no banco de dados!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar status: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum ticket selecionado!", "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        painelDetalhes.add(btnSalvar);
        add(painelDetalhes);

        // Inicializa com o primeiro ticket do banco de dados ou com dados estáticos
        if (tickets != null && !tickets.isEmpty()) {
            atualizarPainelDetalhesComTicket(tickets.get(0), "");
        } else {
            atualizarPainelDetalhes(problemas[0], salas[0], andares[0], areas[0], gravidades[0],
                    problemas[0] + "_" + salas[0]);
        }

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

    /**
     * Método para atualizar o painel de detalhes com um ticket do banco de dados
     * @param ticket O ticket a ser exibido
     * @param andar O andar (pode ser extraído do ticket ou fornecido externamente)
     */
    private void atualizarPainelDetalhesComTicket(Ticket ticket, String andar) {
        if (ticket == null) {
            return;
        }
        
        // Se o andar não foi fornecido, usa um valor padrão
        if (andar == null || andar.isEmpty()) {
            andar = "Não definido";
        }
        
        // Salva o ID do ticket atual
        ticketAtualId = ticket.getId();
        
        String problema = ticket.getDescricao() != null ? ticket.getDescricao() : "";
        String sala = ticket.getSala() != null ? ticket.getSala() : "";
        String area = ticket.getArea() != null ? ticket.getArea().getDescricao() : "Não definida";
        String gravidade = ticket.getPrioridade() != null ? ticket.getPrioridade().getDescricao() : "Não definida";
        
        titulo.setText(problema.toUpperCase());
        labelSala.setText(sala);
        labelAndar.setText(andar);
        labelArea.setText(area);
        labelPrioridade.setText(gravidade);
        
        // Define a cor da prioridade
        if (ticket.getPrioridade() != null) {
            switch (ticket.getPrioridade()) {
                case GRAU_LEVE:
                    labelPrioridade.setBackground(new Color(144, 238, 144));
                    labelPrioridade.setForeground(Color.BLACK);
                    break;
                case GRAU_MEDIO:
                    labelPrioridade.setBackground(Color.YELLOW);
                    labelPrioridade.setForeground(Color.BLACK);
                    break;
                case GRAU_ALTO:
                    labelPrioridade.setBackground(Color.ORANGE);
                    labelPrioridade.setForeground(Color.BLACK);
                    break;
                case GRAU_URGENTE:
                    labelPrioridade.setBackground(Color.RED);
                    labelPrioridade.setForeground(Color.WHITE);
                    break;
                default:
                    labelPrioridade.setBackground(Color.LIGHT_GRAY);
                    labelPrioridade.setForeground(Color.BLACK);
            }
        } else {
            labelPrioridade.setBackground(Color.LIGHT_GRAY);
            labelPrioridade.setForeground(Color.BLACK);
        }
        
        // Define a descrição do ticket
        areaDescricao.setText(ticket.getDescricao());
        
        // Limpa a seleção atual
        chkCiente.setSelected(false);
        chkFazer.setSelected(false);
        chkFinalizado.setSelected(false);
        
        // Verifica o status atual do ticket
        String status = ticket.getStatus();
        if (status != null) {
            switch (status) {
                case "Estou ciente":
                    chkCiente.setSelected(true);
                    break;
                case "A Fazer":
                    chkFazer.setSelected(true);
                    break;
                case "Finalizado":
                    chkFinalizado.setSelected(true);
                    break;
                // Outros status possíveis do banco de dados
                case "Pendente":
                    chkCiente.setSelected(true);
                    break;
                case "Em andamento":
                    chkFazer.setSelected(true);
                    break;
            }
        }
    }
    
    /**
     * Método original para atualizar o painel de detalhes com dados estáticos
     * Mantido para compatibilidade com o código existente
     */
    private void atualizarPainelDetalhes(String problema, int sala, String andar, String area, String gravidade,
            String ticketId) {
        // Salva o ID do ticket atual como null (não é um ticket do banco)
        ticketAtualId = null;

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

    /**
     * Método para atualizar a lista de tickets a partir do banco de dados
     */
    public void atualizarTickets() {
        try {
            // Recarrega os tickets do banco de dados
            tickets = ticketService.listarTodos();
            
            // Atualiza o mapa de status
            if (tickets != null) {
                for (Ticket ticket : tickets) {
                    if (ticket != null && ticket.getDescricao() != null && ticket.getSala() != null) {
                        statusTickets.put(ticket.getDescricao() + "_" + ticket.getSala(), ticket.getStatus());
                    }
                }
                
                // Atualiza a interface se necessário
                if (!tickets.isEmpty()) {
                    atualizarPainelDetalhesComTicket(tickets.get(0), "");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tickets: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgenteDeCampo::new);
    }
}