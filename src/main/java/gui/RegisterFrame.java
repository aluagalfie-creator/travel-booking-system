/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author cedie
 */
import db.UserDB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField userTextField, nameTextField;
    private JPasswordField passField;
    private JButton registerButton;
    private JLabel titleLabel, userLabel, passLabel, nameLabel, messageLabel;
    private UserDB userDB;

    public RegisterFrame(UserDB userDB) {
        this.userDB = userDB;

        setTitle("Travel Booking System - Register");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Manual positioning enabled
        setLayout(null);

        // Title
        titleLabel = new JLabel("Create Customer Account");
        titleLabel.setBounds(130, 20, 200, 30);
        add(titleLabel);

        // Full Name Field
        nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(150, 80, 220, 25);
        add(nameTextField);

        // Username Field
        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 130, 100, 25);
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(150, 130, 220, 25);
        add(userTextField);

        // Password Field
        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 180, 100, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(150, 180, 220, 25);
        add(passField);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBounds(150, 230, 120, 30);
        add(registerButton);

        // Feedback Message Label
        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 280, 320, 25);
        add(messageLabel);

      
    }
    }
