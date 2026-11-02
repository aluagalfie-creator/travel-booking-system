package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;
import service.TravelSystem;

/**
 * Admin screen for managing customers. Layout is null with setBounds on every component.
 */
public class CustomerPanel extends JPanel {
    private JLabel lblTitle, lblName, lblUsername, lblPassword, lblHint;
    private JTextField txtName, txtUsername, txtPassword;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scroll;
    private User[] customers = new User[0];
    private int selectedId = -1;

    public CustomerPanel() {
        setLayout(null);

        lblTitle = new JLabel("Customer Management");
        lblTitle.setBounds(20, 10, 300, 25);
        add(lblTitle);

        lblName = new JLabel("Full Name:");
        lblName.setBounds(20, 50, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(130, 50, 200, 25);
        add(txtName);

        lblUsername = new JLabel("Username:");
        lblUsername.setBounds(20, 85, 100, 25);
        add(lblUsername);
        txtUsername = new JTextField();
        txtUsername.setBounds(130, 85, 200, 25);
        add(txtUsername);

        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(20, 120, 100, 25);
        add(lblPassword);
        txtPassword = new JTextField();
        txtPassword.setBounds(130, 120, 200, 25);
        add(txtPassword);

        lblHint = new JLabel("On update, leave the password empty to keep it.");
        lblHint.setBounds(340, 120, 350, 25);
        add(lblHint);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 160, 80, 30);
        add(btnAdd);
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 160, 80, 30);
        add(btnUpdate);
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(200, 160, 80, 30);
        add(btnDelete);
        btnClear = new JButton("Clear");
        btnClear.setBounds(290, 160, 80, 30);
        add(btnClear);

        model = new DefaultTableModel(new String[]{"ID", "Full Name", "Username"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        scroll = new JScrollPane(table);
        scroll.setBounds(20, 210, 900, 320);
        add(scroll);

        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnClear.addActionListener(e -> clearFields());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFromRow();
            }
        });

        refreshTable();
    }

    private void addCustomer() {
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        if (TravelSystem.customers.getByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "That username is already taken.");
            return;
        }
        User u = new User(0, name, username, password, "CUSTOMER");
        if (TravelSystem.customers.register(u)) {
            JOptionPane.showMessageDialog(this, "Customer added.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Database error. Customer not added.");
        }
    }

    private void updateCustomer() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer from the table first.");
            return;
        }
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (name.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and username cannot be empty.");
            return;
        }
        User old = null;
        for (int i = 0; i < customers.length; i++) {
            if (customers[i].getUserId() == selectedId) {
                old = customers[i];
            }
        }
        if (old == null) {
            return;
        }
        if (password.isEmpty()) {
            password = old.getPassword();
        }
        User other = TravelSystem.customers.getByUsername(username);
        if (other != null && other.getUserId() != selectedId) {
            JOptionPane.showMessageDialog(this, "That username is already taken.");
            return;
        }

        User u = new User(selectedId, name, username, password, "CUSTOMER");
        if (TravelSystem.customers.update(u)) {
            JOptionPane.showMessageDialog(this, "Customer updated.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Database error. Customer not updated.");
        }
    }

    private void deleteCustomer() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer from the table first.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "Delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }
        if (TravelSystem.customers.delete(selectedId)) {
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot delete. The customer may have bookings.");
        }
    }

    private void fillFromRow() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= customers.length) {
            return;
        }
        User u = customers[row];
        selectedId = u.getUserId();
        txtName.setText(u.getName());
        txtUsername.setText(u.getUsername());
        txtPassword.setText("");
    }

    private void clearFields() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    public void refreshTable() {
        User[] all = TravelSystem.customers.getAll();
        int count = 0;
        for (int i = 0; i < all.length; i++) {
            if (all[i].getRole().equals("CUSTOMER")) {
                count++;
            }
        }
        customers = new User[count];
        int k = 0;
        for (int i = 0; i < all.length; i++) {
            if (all[i].getRole().equals("CUSTOMER")) {
                customers[k] = all[i];
                k++;
            }
        }
        model.setRowCount(0);
        for (int i = 0; i < customers.length; i++) {
            model.addRow(new Object[]{customers[i].getUserId(), customers[i].getName(), customers[i].getUsername()});
        }
    }
}
