package br.com.bd_notifica.view;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.TicketService;
import br.com.bd_notifica.services.UserService;
import br.com.bd_notifica.repositories.UserRepository; // Importar UserRepository

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class AdminTicketPanel extends JPanel {

    private TicketService ticketService;
    private UserService userService;
    private UserEntity loggedInUser;

    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private JTextArea statusArea;

    public AdminTicketPanel(TicketService ticketService, UserService userService, UserEntity loggedInUser) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.loggedInUser = loggedInUser;
        
        // Verifica se os serviços foram inicializados corretamente
        if (ticketService == null || userService == null || loggedInUser == null) {
            throw new IllegalArgumentException("Os serviços e o usuário logado não podem ser nulos");
        }
        
        System.out.println("AdminTicketPanel iniciado para usuário: " + loggedInUser.getName());

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Adiciona um painel de título no topo
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Bem-vindo, " + loggedInUser.getName() + " | Painel de Administração");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // --- Painel da Tabela ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Tickets Atuais"));

        String[] columnNames = {"ID", "Descrição", "Sala", "Área", "Prioridade", "Status", "Data Criação", "Usuário"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        ticketTable = new JTable(tableModel);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // --- Painel de Controle (Botões e Busca) ---
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("Ações do Administrador"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: Botões CRUD
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(createButton("Criar Ticket", e -> createTicket()), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        controlPanel.add(createButton("Listar Todos", e -> listAllTickets()), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        controlPanel.add(createButton("Editar Ticket", e -> editTicket()), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        controlPanel.add(createButton("Deletar Ticket", e -> deleteTicket()), gbc);

        // Linha 1: Botões de Busca
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(createButton("Buscar por ID", e -> searchTicketById()), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        controlPanel.add(createButton("Buscar por ID Aluno", e -> searchTicketByStudentId()), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        controlPanel.add(createButton("Buscar por Data", e -> searchTicketByDateRange()), gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        controlPanel.add(createButton("Buscar por Tipo Usuário", e -> searchTicketByUserRole()), gbc);

        // Linha 2: Mais Busca e Contagem
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(createButton("Buscar por Nome Usuário", e -> searchTicketByUserName()), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        controlPanel.add(createButton("Contar por Status", e -> countTicketsByStatus()), gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        controlPanel.add(createButton("Criar Tickets Padrão", e -> {
            try {
                ticketService.criarTicketsPadrao();
                listAllTickets();
                statusArea.setText("Tickets padrão criados com sucesso!");
            } catch (Exception ex) {
                statusArea.setText("Erro ao criar tickets padrão: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao criar tickets padrão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }), gbc);

        // Linha 3: Mensagens de Status
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        statusArea = new JTextArea(3, 40);
        statusArea.setEditable(false);
        statusArea.setWrapStyleWord(true);
        statusArea.setLineWrap(true);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        controlPanel.add(statusScrollPane, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        listAllTickets(); // Carrega todos os tickets na inicialização
    }

    private JButton createButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    private void updateTicketTable(List<Ticket> tickets) {
        tableModel.setRowCount(0); // Limpa os dados existentes
        if (tickets.isEmpty()) {
            statusArea.setText("Nenhum ticket encontrado.");
            return;
        }
        for (Ticket ticket : tickets) {
            tableModel.addRow(new Object[]{
                    ticket.getId(),
                    ticket.getDescricao(),
                    ticket.getSala(),
                    ticket.getArea().getDescricao(),
                    ticket.getPrioridade().getDescricao(),
                    ticket.getStatus(),
                    ticket.getDataCriacao(),
                    ticket.getUser() != null ? ticket.getUser().getName() : "N/A"
            });
        }
        statusArea.setText("Exibindo " + tickets.size() + " ticket(s).");
    }

    private void createTicket() {
        TicketFormDialog dialog = new TicketFormDialog(null, "Criar Novo Ticket", true, null);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Ticket newTicket = dialog.getTicket();
            if (newTicket != null) {
                // Associa o usuário logado ao novo ticket
                newTicket.setUser(loggedInUser);
                try {
                    ticketService.criarTicket(newTicket);
                    statusArea.setText("Ticket criado com sucesso: " + newTicket.getDescricao());
                    listAllTickets();
                } catch (Exception ex) {
                    statusArea.setText("Erro ao criar ticket: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erro ao criar ticket: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void listAllTickets() {
        try {
            List<Ticket> tickets = ticketService.listarTodos();
            updateTicketTable(tickets);
        } catch (Exception ex) {
            statusArea.setText("Erro ao listar tickets: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Erro ao listar tickets: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTicket() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um ticket para editar.", "Nenhum Ticket Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long ticketId = (Long) tableModel.getValueAt(selectedRow, 0);
        Ticket existingTicket = ticketService.buscarPorId(ticketId);

        if (existingTicket != null) {
            TicketFormDialog dialog = new TicketFormDialog(null, "Editar Ticket", true, existingTicket);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                Ticket updatedTicket = dialog.getTicket();
                if (updatedTicket != null) {
                    // Mantém o usuário original e a data de criação
                    updatedTicket.setUser(existingTicket.getUser());
                    updatedTicket.setDataCriacao(existingTicket.getDataCriacao());
                    
                    try {
                        ticketService.editar(updatedTicket);
                        statusArea.setText("Ticket " + updatedTicket.getId() + " editado com sucesso.");
                        listAllTickets();
                    } catch (Exception ex) {
                        statusArea.setText("Erro ao editar ticket: " + ex.getMessage());
                        JOptionPane.showMessageDialog(this, "Erro ao editar ticket: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            statusArea.setText("Ticket selecionado não encontrado no banco de dados.");
            JOptionPane.showMessageDialog(this, "Ticket não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTicket() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um ticket para deletar.", "Nenhum Ticket Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long ticketId = (Long) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o ticket ID: " + ticketId + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ticketService.deletar(ticketId);
                statusArea.setText("Ticket " + ticketId + " deletado com sucesso.");
                listAllTickets();
            } catch (Exception ex) {
                statusArea.setText("Erro ao deletar ticket: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao deletar ticket: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTicketById() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Ticket:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idStr.trim());
                Ticket foundTicket = ticketService.buscarPorId(id);
                if (foundTicket != null) {
                    updateTicketTable(List.of(foundTicket));
                    statusArea.setText("Ticket ID " + id + " encontrado.");
                } else {
                    updateTicketTable(List.of()); // Limpa a tabela
                    statusArea.setText("Ticket com ID " + id + " não encontrado.");
                    JOptionPane.showMessageDialog(this, "Ticket com ID " + id + " não encontrado.", "Ticket Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                statusArea.setText("ID inválido. Por favor, digite um número.");
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                statusArea.setText("Erro ao buscar ticket por ID: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao buscar ticket por ID: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTicketByStudentId() {
        String studentIdStr = JOptionPane.showInputDialog(this, "Digite o ID do Aluno:");
        if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
            try {
                Long studentId = Long.parseLong(studentIdStr.trim());
                List<Ticket> tickets = ticketService.buscarPorAlunoId(studentId);
                updateTicketTable(tickets);
                if (tickets.isEmpty()) {
                    statusArea.setText("Nenhum ticket encontrado para o Aluno ID: " + studentId + ".");
                    JOptionPane.showMessageDialog(this, "Nenhum ticket encontrado para o Aluno ID: " + studentId + ".", "Nenhum Ticket", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusArea.setText("Exibindo tickets para o Aluno ID: " + studentId + ".");
                }
            } catch (NumberFormatException ex) {
                statusArea.setText("ID do Aluno inválido. Por favor, digite um número.");
                JOptionPane.showMessageDialog(this, "ID do Aluno inválido. Por favor, digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                statusArea.setText("Erro ao buscar tickets por ID do Aluno: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao buscar tickets por ID do Aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTicketByDateRange() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        panel.add(new JLabel("Data Inicial (AAAA-MM-DD):"));
        panel.add(startDateField);
        panel.add(new JLabel("Data Final (AAAA-MM-DD):"));
        panel.add(endDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Buscar Tickets por Intervalo de Datas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDate startDate = LocalDate.parse(startDateField.getText().trim());
                LocalDate endDate = LocalDate.parse(endDateField.getText().trim());

                List<Ticket> tickets = ticketService.buscarPorIntervalo(startDate, endDate);
                updateTicketTable(tickets);
                if (tickets.isEmpty()) {
                    statusArea.setText("Nenhum ticket encontrado no intervalo de " + startDate + " a " + endDate + ".");
                    JOptionPane.showMessageDialog(this, "Nenhum ticket encontrado no intervalo de " + startDate + " a " + endDate + ".", "Nenhum Ticket", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusArea.setText("Exibindo tickets de " + startDate + " a " + endDate + ".");
                }
            } catch (DateTimeParseException ex) {
                statusArea.setText("Formato de data inválido. Use AAAA-MM-DD.");
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                statusArea.setText("Erro ao buscar tickets por intervalo de datas: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao buscar tickets por intervalo de datas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTicketByUserRole() {
        String[] roles = {UserRole.ADMIN.name(), UserRole.STUDENT.name(), UserRole.AGENT.name()};
        String selectedRole = (String) JOptionPane.showInputDialog(this,
                "Selecione o tipo de usuário:",
                "Buscar por Tipo de Usuário",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles,
                roles[0]);

        if (selectedRole != null && !selectedRole.trim().isEmpty()) {
            try {
                List<Ticket> tickets = ticketService.buscarPorTipoUsuario(selectedRole);
                updateTicketTable(tickets);
                if (tickets.isEmpty()) {
                    statusArea.setText("Nenhum ticket encontrado para o tipo de usuário: " + selectedRole + ".");
                    JOptionPane.showMessageDialog(this, "Nenhum ticket encontrado para o tipo de usuário: " + selectedRole + ".", "Nenhum Ticket", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusArea.setText("Exibindo tickets para o tipo de usuário: " + selectedRole + ".");
                }
            } catch (IllegalArgumentException ex) {
                statusArea.setText(ex.getMessage());
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                statusArea.setText("Erro ao buscar tickets por tipo de usuário: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao buscar tickets por tipo de usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTicketByUserName() {
        String namePart = JOptionPane.showInputDialog(this, "Digite parte do nome do usuário:");
        if (namePart != null && !namePart.trim().isEmpty()) {
            try {
                List<Ticket> tickets = ticketService.buscarPorNomeUsuario(namePart.trim());
                updateTicketTable(tickets);
                if (tickets.isEmpty()) {
                    statusArea.setText("Nenhum ticket encontrado para usuários com '" + namePart + "' no nome.");
                    JOptionPane.showMessageDialog(this, "Nenhum ticket encontrado para usuários com '" + namePart + "' no nome.", "Nenhum Ticket", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusArea.setText("Exibindo tickets para usuários com '" + namePart + "' no nome.");
                }
            } catch (Exception ex) {
                statusArea.setText("Erro ao buscar tickets por nome de usuário: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao buscar tickets por nome de usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void countTicketsByStatus() {
        try {
            List<Object[]> statusCounts = ticketService.contarChamadosPorStatus();
            StringBuilder sb = new StringBuilder("Quantidade de Tickets por Status:\n");
            if (statusCounts.isEmpty()) {
                sb.append("Nenhum ticket encontrado para contagem.");
            } else {
                for (Object[] row : statusCounts) {
                    sb.append("Status: ").append(row[0]).append(" | Quantidade: ").append(row[1]).append("\n");
                }
            }
            statusArea.setText(sb.toString());
            JOptionPane.showMessageDialog(this, sb.toString(), "Contagem de Tickets por Status", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            statusArea.setText("Erro ao contar tickets por status: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Erro ao contar tickets por status: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}