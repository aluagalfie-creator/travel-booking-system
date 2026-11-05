package gui;

import db.HotelDB;
import ds.PackageSorter;
import java.sql.SQLException;
import javax.swing.*;
import model.Flight;
import model.Hotel;
import model.PackageDeal;
import service.TravelSystem;


public class PackagePanel extends JPanel {
    private static final int MAX = 100;
    private static PackageDeal[] store = new PackageDeal[MAX];
    private static int count = 0;
    private static int nextId = 1;

    private JLabel lblTitle, lblName, lblFlightId, lblHotelId, lblNights;
    private JTextField txtName, txtFlightId, txtHotelId, txtNights;
    private JButton btnAdd, btnDelete, btnSortPrice, btnSortRating, btnBook, btnClear;
    private JList<String> packageList;
    private DefaultListModel<String> listModel;
    private PackageDeal[] shown = new PackageDeal[0];
    private boolean priceAscending = true;
    private boolean isAdmin;

    public PackagePanel() {
        setLayout(null);
        listModel = new DefaultListModel<>();
        isAdmin = TravelSystem.currentUser != null && TravelSystem.currentUser.getRole().equals("ADMIN");

        lblTitle = new JLabel("PACKAGE DEALS");
        lblTitle.setBounds(20, 10, 300, 25);
        add(lblTitle);

        lblName = new JLabel("Package Name:");
        lblName.setBounds(20, 50, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(130, 50, 200, 25);
        add(txtName);

        lblFlightId = new JLabel("Flight ID:");
        lblFlightId.setBounds(20, 85, 100, 25);
        add(lblFlightId);
        txtFlightId = new JTextField();
        txtFlightId.setBounds(130, 85, 200, 25);
        add(txtFlightId);

        lblHotelId = new JLabel("Hotel ID:");
        lblHotelId.setBounds(20, 120, 100, 25);
        add(lblHotelId);
        txtHotelId = new JTextField();
        txtHotelId.setBounds(130, 120, 200, 25);
        add(txtHotelId);

        lblNights = new JLabel("Nights:");
        lblNights.setBounds(20, 155, 100, 25);
        add(lblNights);
        txtNights = new JTextField("1");
        txtNights.setBounds(130, 155, 200, 25);
        add(txtNights);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 195, 90, 30);
        add(btnAdd);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(120, 195, 90, 30);
        add(btnDelete);

        btnSortPrice = new JButton("Sort Price");
        btnSortPrice.setBounds(220, 195, 110, 30);
        add(btnSortPrice);

        btnSortRating = new JButton("Sort Rating");
        btnSortRating.setBounds(340, 195, 110, 30);
        add(btnSortRating);

        btnBook = new JButton("Book");
        btnBook.setBounds(460, 195, 90, 30);
        add(btnBook);

        btnClear = new JButton("Clear");
        btnClear.setBounds(560, 195, 90, 30);
        add(btnClear);

        packageList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(packageList);
        scrollpane.setBounds(20, 245, 900, 285);
        add(scrollpane);

        // Admin builds packages, customers book them.
        lblName.setVisible(isAdmin);
        txtName.setVisible(isAdmin);
        lblFlightId.setVisible(isAdmin);
        txtFlightId.setVisible(isAdmin);
        lblHotelId.setVisible(isAdmin);
        txtHotelId.setVisible(isAdmin);
        lblNights.setVisible(isAdmin);
        txtNights.setVisible(isAdmin);
        btnAdd.setVisible(isAdmin);
        btnDelete.setVisible(isAdmin);
        btnClear.setVisible(isAdmin);
        btnBook.setVisible(!isAdmin);

        btnAdd.addActionListener(e -> addPackage());
        btnDelete.addActionListener(e -> deletePackage());
        btnSortPrice.addActionListener(e -> sortPrice());
        btnSortRating.addActionListener(e -> sortRating());
        btnBook.addActionListener(e -> bookPackage());
        btnClear.addActionListener(e -> clearFields());

        showStore();
    }

    private void addPackage() {
        String name = txtName.getText().trim();
        String flightText = txtFlightId.getText().trim();
        String hotelText = txtHotelId.getText().trim();
        String nightsText = txtNights.getText().trim();

        if (name.isEmpty() || flightText.isEmpty() || hotelText.isEmpty() || nightsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        int flightId;
        int hotelId;
        int nights;
        try {
            flightId = Integer.parseInt(flightText);
            hotelId = Integer.parseInt(hotelText);
            nights = Integer.parseInt(nightsText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Flight ID, hotel ID and nights must be numbers.");
            return;
        }
        if (nights < 1) {
            JOptionPane.showMessageDialog(this, "Nights must be at least 1.");
            return;
        }
        Flight f = TravelSystem.flights.getById(flightId);
        if (f == null) {
            JOptionPane.showMessageDialog(this, "Flight ID " + flightId + " does not exist.");
            return;
        }
        Hotel h = HotelDB.getById(hotelId);
        if (h == null) {
            JOptionPane.showMessageDialog(this, "Hotel ID " + hotelId + " does not exist.");
            return;
        }
        if (count == MAX) {
            JOptionPane.showMessageDialog(this, "The package list is full.");
            return;
        }

        store[count] = new PackageDeal(nextId, name, f, h, nights);
        count++;
        nextId++;
        JOptionPane.showMessageDialog(this, "Package added.");
        clearFields();
        showStore();
    }

    private void deletePackage() {
        int index = packageList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a package first.");
            return;
        }
        int id = shown[index].getId();
        for (int i = 0; i < count; i++) {
            if (store[i].getId() == id) {
                for (int j = i; j < count - 1; j++) {
                    store[j] = store[j + 1];
                }
                store[count - 1] = null;
                count--;
                break;
            }
        }
        showStore();
    }

    private void sortPrice() {
        copyStore();
        PackageSorter.sortByPrice(shown, priceAscending);
        priceAscending = !priceAscending;
        fillList();
    }

    private void sortRating() {
        copyStore();
        PackageSorter.sortByRating(shown);
        fillList();
    }

    private void bookPackage() {
        int index = packageList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a package first.");
            return;
        }
        if (TravelSystem.currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }
        PackageDeal p = shown[index];
        try {
            TravelSystem.bookings.book(TravelSystem.currentUser.getUserId(),
                    p.getFlight().getFlightId(), p.getHotel().getHotelId(), p.getNights());
            JOptionPane.showMessageDialog(this, "Booked! Total: " + String.format("%.2f", p.getTotalPrice())
                    + ". See My Bookings for the invoice.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtFlightId.setText("");
        txtHotelId.setText("");
        txtNights.setText("1");
    }

    private void copyStore() {
        shown = new PackageDeal[count];
        for (int i = 0; i < count; i++) {
            shown[i] = store[i];
        }
    }

    private void showStore() {
        copyStore();
        fillList();
    }

    private void fillList() {
        listModel.clear();
        for (int i = 0; i < shown.length; i++) {
            PackageDeal p = shown[i];
            listModel.addElement("Package #" + p.getId() + ": " + p.getName()
                    + " | " + p.getFlight().getOrigin() + " to " + p.getFlight().getDestination()
                    + " | " + p.getHotel().getName()
                    + " | " + p.getNights() + " night(s)"
                    + " | Total: " + String.format("%.2f", p.getTotalPrice())
                    + " | Rating: " + p.getRating());
        }
    }
}
