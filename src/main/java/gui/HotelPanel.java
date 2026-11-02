package gui;

import db.HotelDB;
import model.Hotel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Admin screen for managing hotels. Layout is null with setBounds on every component.
 */
public class HotelPanel extends JPanel {
    private JLabel lblTitle, lblName, lblLocation, lblPrice, lblRating;
    private JTextField txtName, txtLocation, txtPrice, txtRating;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scroll;
    private Hotel[] hotels = new Hotel[0];
    private int selectedId = -1;

    public HotelPanel() {
        setLayout(null);

        lblTitle = new JLabel("Hotel Management");
        lblTitle.setBounds(20, 10, 300, 25);
        add(lblTitle);

        lblName = new JLabel("Name:");
        lblName.setBounds(20, 50, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(130, 50, 200, 25);
        add(txtName);

        lblLocation = new JLabel("Location:");
        lblLocation.setBounds(20, 85, 100, 25);
        add(lblLocation);
        txtLocation = new JTextField();
        txtLocation.setBounds(130, 85, 200, 25);
        add(txtLocation);

        lblPrice = new JLabel("Price/Night:");
        lblPrice.setBounds(20, 120, 100, 25);
        add(lblPrice);
        txtPrice = new JTextField();
        txtPrice.setBounds(130, 120, 200, 25);
        add(txtPrice);

        lblRating = new JLabel("Rating (0-5):");
        lblRating.setBounds(20, 155, 100, 25);
        add(lblRating);
        txtRating = new JTextField();
        txtRating.setBounds(130, 155, 200, 25);
        add(txtRating);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 195, 80, 30);
        add(btnAdd);
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 195, 80, 30);
        add(btnUpdate);
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(200, 195, 80, 30);
        add(btnDelete);
        btnClear = new JButton("Clear");
        btnClear.setBounds(290, 195, 80, 30);
        add(btnClear);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Location", "Price/Night", "Rating"}, 0);
        table = new JTable(model);
        scroll = new JScrollPane(table);
        scroll.setBounds(20, 240, 640, 200);
        add(scroll);

        btnAdd.addActionListener(e -> addHotel());
        btnUpdate.addActionListener(e -> updateHotel());
        btnDelete.addActionListener(e -> deleteHotel());
        btnClear.addActionListener(e -> clearFields());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFromRow();
            }
        });

        refreshTable();
    }

    private Hotel readInput() {
        String name = txtName.getText().trim();
        String location = txtLocation.getText().trim();
        if (name.isEmpty() || location.isEmpty()
                || txtPrice.getText().trim().isEmpty() || txtRating.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return null;
        }
        double price;
        double rating;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
            rating = Double.parseDouble(txtRating.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and rating must be numbers.");
            return null;
        }
        if (price < 0) {
            JOptionPane.showMessageDialog(this, "Price cannot be negative.");
            return null;
        }
        if (rating < 0 || rating > 5) {
            JOptionPane.showMessageDialog(this, "Rating must be between 0 and 5.");
            return null;
        }
        Hotel h = new Hotel(0, "","",0,0);
        h.setName(name);
        h.setLocation(location);
        h.setPricePerNight(price);
        h.setRating(rating);
        return h;
    }

    private void addHotel() {
        Hotel h = readInput();
        if (h == null) {
            return;
        }
        if (HotelDB.addHotel(h)) {
            JOptionPane.showMessageDialog(this, "Hotel added.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Database error. Hotel not added.");
        }
    }

    private void updateHotel() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a hotel from the table first.");
            return;
        }
        Hotel h = readInput();
        if (h == null) {
            return;
        }
        h.setHotelId(selectedId);
        if (HotelDB.updateHotel(h)) {
            JOptionPane.showMessageDialog(this, "Hotel updated.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Database error. Hotel not updated.");
        }
    }

    private void deleteHotel() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a hotel from the table first.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "Delete this hotel?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }
        if (HotelDB.deleteHotel(selectedId)) {
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot delete. The hotel may be used in a booking.");
        }
    }

    private void fillFromRow() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= hotels.length) {
            return;
        }
        Hotel h = hotels[row];
        selectedId = h.getHotelId();
        txtName.setText(h.getName());
        txtLocation.setText(h.getLocation());
        txtPrice.setText(String.valueOf(h.getPricePerNight()));
        txtRating.setText(String.valueOf(h.getRating()));
    }

    private void clearFields() {
        txtName.setText("");
        txtLocation.setText("");
        txtPrice.setText("");
        txtRating.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    public void refreshTable() {
        hotels = HotelDB.getAllHotels();
        model.setRowCount(0);
        for (int i = 0; i < hotels.length; i++) {
            Hotel h = hotels[i];
            model.addRow(new Object[]{h.getHotelId(), h.getName(), h.getLocation(),
                h.getPricePerNight(), h.getRating()});
        }
    }
}
