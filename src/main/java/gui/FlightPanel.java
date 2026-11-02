/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Flight;
import service.FlightCatalog;
/**
 *
 * @author Alyanna
 */
public class FlightPanel extends JPanel{
     private JTextField originField, destinationField, departureField, arrivalField, priceField;
    private JTextField searchField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private JButton searchButton, showAllButton;
    private JLabel titleLabel, originLabel, destinationLabel, departureLabel,
                   arrivalLabel, priceLabel, departureHint, arrivalHint, searchLabel;
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScroll;

    private FlightCatalog catalog;
    private int selectedId = 0;  

    private DateTimeFormatter dateFormat = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public FlightPanel(FlightCatalog catalog) {
        this.catalog = catalog;

        setLayout(null);

        titleLabel = new JLabel("Manage Flights");
        titleLabel.setBounds(20, 10, 200, 30);
        add(titleLabel);

        originLabel = new JLabel("Origin:");
        originLabel.setBounds(20, 50, 100, 25);
        add(originLabel);

        originField = new JTextField();
        originField.setBounds(120, 50, 180, 25);
        add(originField);

        destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(20, 85, 100, 25);
        add(destinationLabel);

        destinationField = new JTextField();
        destinationField.setBounds(120, 85, 180, 25);
        add(destinationField);

        departureLabel = new JLabel("Departure:");
        departureLabel.setBounds(20, 120, 100, 25);
        add(departureLabel);

        departureField = new JTextField();
        departureField.setBounds(120, 120, 180, 25);
        add(departureField);

        arrivalLabel = new JLabel("Arrival:");
        arrivalLabel.setBounds(20, 155, 100, 25);
        add(arrivalLabel);

        arrivalField = new JTextField();
        arrivalField.setBounds(120, 155, 180, 25);
        add(arrivalField);

        priceLabel = new JLabel("Price:");
        priceLabel.setBounds(20, 190, 100, 25);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(120, 190, 180, 25);
        add(priceField);

        departureHint = new JLabel("e.g. 2026-11-05 14:30");
        departureHint.setBounds(310, 120, 200, 25);
        departureHint.setFont(departureHint.getFont().deriveFont(java.awt.Font.PLAIN, 11f));
        add(departureHint);

        arrivalHint = new JLabel("e.g. 2026-11-05 16:00");
        arrivalHint.setBounds(310, 155, 200, 25);
        arrivalHint.setFont(arrivalHint.getFont().deriveFont(java.awt.Font.PLAIN, 11f));
        add(arrivalHint);

        addButton = new JButton("Add");
        addButton.setBounds(20, 230, 80, 30);
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(110, 230, 80, 30);
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, 230, 80, 30);
        add(deleteButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(290, 230, 80, 30);
        add(clearButton);

        searchLabel = new JLabel("Search destination:");
        searchLabel.setBounds(420, 50, 130, 25);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(550, 50, 130, 25);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(690, 50, 80, 25);
        add(searchButton);

        showAllButton = new JButton("Show All");
        showAllButton.setBounds(670, 85, 100, 25);
        add(showAllButton);

        // (cells can't be typed into, only clicked)
        String[] columns = {"ID", "Departure", "Arrival", "Origin", "Destination", "Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightTable = new JTable(tableModel);
        tableScroll = new JScrollPane(flightTable);
        tableScroll.setBounds(20, 280, 750, 200);
        add(tableScroll);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFlight();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFlight();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFlight();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFlights();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                refreshTable(catalog.getAllSortedByDeparture());
            }
        });

        flightTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loadSelectedRow();
            }
        });

        refreshTable(catalog.getAllSortedByDeparture());
    }

    private void addFlight() {
        Flight f = readForm(0);
        if (f == null) {
            return;   
        }
        if (catalog.addFlight(f)) {
            JOptionPane.showMessageDialog(this, "Flight added.");
            clearForm();
            refreshTable(catalog.getAllSortedByDeparture());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not save the flight. Check that MySQL is running.");
        }
    }

    private void updateFlight() {
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(this, "Click a flight in the table first.");
            return;
        }
        Flight f = readForm(selectedId);
        if (f == null) {
            return;
        }
        if (catalog.updateFlight(f)) {
            JOptionPane.showMessageDialog(this, "Flight updated.");
            clearForm();
            refreshTable(catalog.getAllSortedByDeparture());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not update the flight. Check that MySQL is running.");
        }
    }

    private void deleteFlight() {
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(this, "Click a flight in the table first.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete this flight?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        if (catalog.deleteFlight(selectedId)) {
            JOptionPane.showMessageDialog(this, "Flight deleted.");
            clearForm();
            refreshTable(catalog.getAllSortedByDeparture());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not delete the flight. Check that MySQL is running.");
        }
    }

    private void searchFlights() {
        String destination = searchField.getText().trim();
        if (destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a destination to search.");
            return;
        }
        ArrayList<Flight> results = catalog.searchByDestination(destination);
        refreshTable(results);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights found for " + destination + ".");
        }
    }

    private Flight readForm(int id) {
        String origin = originField.getText().trim();
        String destination = destinationField.getText().trim();
        String departure = departureField.getText().trim();
        String arrival = arrivalField.getText().trim();
        String priceText = priceField.getText().trim();

        if (origin.isEmpty() || destination.isEmpty() || departure.isEmpty()
                || arrival.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return null;
        }

        LocalDateTime departTime;
        LocalDateTime arriveTime;
        try {
            departTime = LocalDateTime.parse(departure, dateFormat);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Departure must look like 2026-11-05 14:30");
            return null;
        }
        try {
            arriveTime = LocalDateTime.parse(arrival, dateFormat);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Arrival must look like 2026-11-05 16:00");
            return null;
        }
        if (!arriveTime.isAfter(departTime)) {
            JOptionPane.showMessageDialog(this, "Arrival must be after departure.");
            return null;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.");
            return null;
        }
        if (price < 0) {
            JOptionPane.showMessageDialog(this, "Price cannot be negative.");
            return null;
        }

        return new Flight(id, departure, arrival, origin, destination, price);
    }

    private void refreshTable(ArrayList<Flight> flights) {
        tableModel.setRowCount(0); 
        for (Flight f : flights) {
            Object[] row = {f.getFlightId(), f.getDeparture(), f.getArrival(),
                            f.getOrigin(), f.getDestination(), f.getPrice()};
            tableModel.addRow(row);
        }
    }

    private void loadSelectedRow() {
        int row = flightTable.getSelectedRow();
        if (row == -1) {
            return;
        }
        selectedId = (int) tableModel.getValueAt(row, 0);
        departureField.setText(tableModel.getValueAt(row, 1).toString());
        arrivalField.setText(tableModel.getValueAt(row, 2).toString());
        originField.setText(tableModel.getValueAt(row, 3).toString());
        destinationField.setText(tableModel.getValueAt(row, 4).toString());
        priceField.setText(tableModel.getValueAt(row, 5).toString());
    }

    private void clearForm() {
        selectedId = 0;
        originField.setText("");
        destinationField.setText("");
        departureField.setText("");
        arrivalField.setText("");
        priceField.setText("");
        flightTable.clearSelection();
    }
}
