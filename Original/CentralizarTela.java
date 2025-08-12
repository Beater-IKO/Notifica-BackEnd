import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CentralizarTela extends JFrame {
    
    public CentralizarTela() {
        setTitle("Tela Centralizada");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        // Adiciona listener para centralizar quando a tela for aberta
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                centralizarTela();
            }
        });
        
        centralizarTela(); // Centraliza na primeira abertura
    }
    
    private void centralizarTela() {
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CentralizarTela().setVisible(true);
        });
    }
}