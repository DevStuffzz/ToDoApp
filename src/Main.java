import todoapp.TodoApp;

import javax.swing.*;
import java.util.Scanner;

/**
 * Entry Point of the Application.
 * Supports GUI or headless mode (console only).
 * Adds a login screen with hardcoded credentials.
 *
 * Username: admin
 * Password: 1234
 */
public class Main {

    public static void main(String[] args) {
        boolean headless = false;

        // Check CLI args for headless flag
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--headless")) {
                headless = true;
            }
        }

        // Login depending on mode
        boolean loggedIn = false;
        if (!headless) {
            // GUI login
            loggedIn = guiLogin();
        } else {
            // Console login
            loggedIn = consoleLogin();
        }

        if (!loggedIn) {
            System.out.println("Login failed. Exiting...");
            System.exit(1);
        }

        // Launch the app (headless flag passed)
        new TodoApp(headless);
    }

    /**
     * GUI login using Swing dialog.
     */
    private static boolean guiLogin() {
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(10);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(10);

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            return username.equals("admin") && password.equals("1234");
        }
        return false;
    }

    /**
     * Console login (runs in same thread for headless mode).
     */
    private static boolean consoleLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TodoApp Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        return username.equals("admin") && password.equals("1234");
    }
}
