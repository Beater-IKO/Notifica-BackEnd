
package br.com.bd_notifica;

import br.com.bd_notifica.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setLocationRelativeTo(null); // centraliza na tela
            login.setVisible(true);
        });
    }
}
