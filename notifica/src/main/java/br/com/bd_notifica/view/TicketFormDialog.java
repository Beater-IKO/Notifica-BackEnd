package br.com.bd_notifica.view;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.Prioridade;

import javax.swing.*;
import java.awt.*;

public class TicketFormDialog extends JDialog {

    private JTextField descriptionField;
    private JTextField roomField;
    private JComboBox<Area> areaComboBox;
    private JComboBox<Prioridade> priorityComboBox;
    private JComboBox<String> statusComboBox; // Adicionado para status no modo de edição

    private Ticket ticket;
    private boolean confirmed = false;

    public TicketFormDialog(Frame owner, String title, boolean modal, Ticket existingTicket) {
        super(owner, title, modal);
        this.ticket = existingTicket;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // Linhas ajustadas para status
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        descriptionField = new JTextField(30);
        roomField = new JTextField(15);
        areaComboBox = new JComboBox<>(Area.values());
        priorityComboBox = new JComboBox<>(Prioridade.values());
        statusComboBox = new JComboBox<>(new String[]{"Pendente", "Lido", "Em andamento", "Finalizado"}); // Todos os status possíveis

        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Sala:"));
        formPanel.add(roomField);
        formPanel.add(new JLabel("Área:"));
        formPanel.add(areaComboBox);
        formPanel.add(new JLabel("Prioridade:"));
        formPanel.add(priorityComboBox);

        if (ticket != null) { // Se estiver editando um ticket existente
            formPanel.add(new JLabel("Status:"));
            formPanel.add(statusComboBox);
            populateFields();
        } else { // Se estiver criando um novo ticket, o status é 'Pendente' por padrão e não editável
            statusComboBox.setSelectedItem("Pendente");
            statusComboBox.setEnabled(false); // Status não é editável na criação
        }


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            if (validateInputs()) {
                saveTicket();
                confirmed = true;
                dispose();
            }
        });
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    private void populateFields() {
        if (ticket != null) {
            descriptionField.setText(ticket.getDescricao());
            roomField.setText(ticket.getSala());
            areaComboBox.setSelectedItem(ticket.getArea());
            priorityComboBox.setSelectedItem(ticket.getPrioridade());
            statusComboBox.setSelectedItem(ticket.getStatus());
        }
    }

    private boolean validateInputs() {
        if (descriptionField.getText().trim().isEmpty() ||
            roomField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descrição e Sala não podem ser vazios.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveTicket() {
        if (ticket == null) {
            ticket = new Ticket();
        }
        ticket.setDescricao(descriptionField.getText().trim());
        ticket.setSala(roomField.getText().trim());
        ticket.setArea((Area) areaComboBox.getSelectedItem());
        ticket.setPrioridade((Prioridade) priorityComboBox.getSelectedItem());
        ticket.setStatus((String) statusComboBox.getSelectedItem()); // Define o status da combobox
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}