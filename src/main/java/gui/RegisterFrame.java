package gui;

import javax.swing.*;
import model.User;
import service.TravelSystem;

public class RegisterFrame extends JFrame {
    private JTextField userTextField, nameTextField;
    private JPasswordField passField;
    private JButton registerButton, backButton;
    private JLabel titleLabel, userLabel, passLabel, nameLabel;

    public RegisterFrame() {
        setTitle("Travel Booking System - Register");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Manual positioning enabled
        setLayout(null);

        titleLabel = new JLabel("Create Customer Account");
        titleLabel.setBounds(130, 20, 200, 30);
        add(titleLabel);

        nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(150, 80, 220, 25);
        add(nameTextField);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 130, 100, 25);
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(150, 130, 220, 25);
        add(userTextField);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 180, 100, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(150, 180, 220, 25);
        add(passField);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 230, 100, 30);
        add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(270, 230, 100, 30);
        add(backButton);

        registerButton.addActionListener(e -> register());
        backButton.addActionListener(e -> dispose());
    }

    private void register() {
        String fullName = nameTextField.getText().trim();
        String username = userTextField.getText().trim();
        String password = new String(passField.getPassword());

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        if (TravelSystem.customers.getByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "That username is already taken.");
            return;
        }

        User u = new User(0, fullName, username, password, "CUSTOMER");
        if (TravelSystem.customers.register(u)) {
            JOptionPane.showMessageDialog(this, "Account created. You can log in now.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Database error. Account not created.");
        }
    }
}
