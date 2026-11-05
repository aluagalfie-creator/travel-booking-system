package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.User;
import service.TravelSystem;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel lblTitle, lblUser, lblPass;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnRegister;

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 320);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lblTitle = new JLabel("Travel Booking System");
        lblTitle.setBounds(100, 20, 220, 30);
        add(lblTitle);

        lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 80, 80, 25);
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(140, 80, 200, 25);
        add(txtUser);

        lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 120, 80, 25);
        add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(140, 120, 200, 25);
        add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 170, 95, 30);
        add(btnLogin);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(245, 170, 95, 30);
        add(btnRegister);

        //Add to ActionListener
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                User u = TravelSystem.customers.getByUsername(username); //BE
                if (u != null && u.getPassword().equals(password)) {
                    TravelSystem.currentUser = u;
                    new MainFrame(u.getRole()).setVisible(true); //FE
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnRegister) {
            new RegisterFrame().setVisible(true); //FE
        }
    }
}
