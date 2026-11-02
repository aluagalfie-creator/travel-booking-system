package gui;

import javax.swing.*;
import model.User;
import service.TravelSystem;

public class LoginFrame extends JFrame {
    private JLabel lblTitle = new JLabel("Travel Booking System");
    private JLabel lblUser = new JLabel("Username:");
    private JLabel lblPass = new JLabel("Password:");
    private JTextField txtUser = new JTextField();
    private JPasswordField txtPass = new JPasswordField();
    private JButton btnLogin = new JButton("Login");
    private JButton btnRegister = new JButton("Register");

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 320);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lblTitle.setBounds(100, 20, 220, 30);
        lblUser.setBounds(50, 80, 80, 25);
        txtUser.setBounds(140, 80, 200, 25);
        lblPass.setBounds(50, 120, 80, 25);
        txtPass.setBounds(140, 120, 200, 25);
        btnLogin.setBounds(140, 170, 95, 30);
        btnRegister.setBounds(245, 170, 95, 30);

        add(lblTitle);
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);
        add(btnRegister);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());
    }

    private void login() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        User u = TravelSystem.customers.getByUsername(username);
        if (u == null || !u.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Wrong username or password.");
            return;
        }

        TravelSystem.currentUser = u;
        new MainFrame(u.getRole()).setVisible(true);
        dispose();
    }

    private void register() {
        new RegisterFrame().setVisible(true);
    }
}
