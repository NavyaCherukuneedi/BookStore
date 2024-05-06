package bookstore;

import javax.swing.SwingUtilities;

import bookstore.loginWindow.LoginWindow;
import bookstore.ribbonLayout.RibbonLayout;

public class MainApplication {
    public static void main(String[] args) {
        //  SwingUtilities.invokeLater(LoginWindow::new);
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.dispose(); // Close the login window
            new RibbonLayout(); // Open the main application window
        });
    }
}