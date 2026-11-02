package gui;

import db.HotelDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;
import model.Booking;
import model.Flight;
import model.Hotel;
import service.TravelSystem;

public class BookingPanel extends JPanel implements ActionListener {
    private JLabel lblTitle, lblUser, lblFlights, lblHotels, lblFlightId, lblHotelId, lblNights, lblNote;
    private JTextField txtFlightId, txtHotelId, txtNights;
    private JButton btnBook, btnCancel, btnInvoice, btnClear, btnRefresh;
    private JList<String> flightList, hotelList, bookingList;
    private DefaultListModel<String> flightModel, hotelModel, listModel;
    private ArrayList<Flight> flightsShown;
    private Hotel[] hotelsShown;
    private LinkedList<Booking> shown;

    public BookingPanel() {
        setLayout(null);
        flightModel = new DefaultListModel<>();
        hotelModel = new DefaultListModel<>();
        listModel = new DefaultListModel<>();
        flightsShown = new ArrayList<>();
        hotelsShown = new Hotel[0];
        shown = new LinkedList<>();

        lblTitle = new JLabel("BOOK A TRIP");
        lblTitle.setBounds(400, 5, 250, 25);
        add(lblTitle);

        String UserName = "nobody";
        if (TravelSystem.currentUser != null) {
            UserName = TravelSystem.currentUser.getName();
        }
        lblUser = new JLabel("Booking as: " + UserName);
        lblUser.setBounds(50, 30, 300, 25);
        add(lblUser);

        lblFlights = new JLabel("Available flights (click one to pick it)");
        lblFlights.setBounds(50, 60, 400, 25);
        add(lblFlights);

        lblHotels = new JLabel("Available hotels (click one to pick it)");
        lblHotels.setBounds(500, 60, 400, 25);
        add(lblHotels);

        flightList = new JList<String>(flightModel);
        JScrollPane flightScroll = new JScrollPane(flightList);
        flightScroll.setBounds(50, 85, 430, 120);
        add(flightScroll);

        hotelList = new JList<String>(hotelModel);
        JScrollPane hotelScroll = new JScrollPane(hotelList);
        hotelScroll.setBounds(500, 85, 410, 120);
        add(hotelScroll);

        lblFlightId = new JLabel("Flight ID:");
        lblFlightId.setBounds(50, 215, 70, 25);
        add(lblFlightId);

        txtFlightId = new JTextField("0");
        txtFlightId.setBounds(120, 215, 80, 25);
        add(txtFlightId);

        lblHotelId = new JLabel("Hotel ID:");
        lblHotelId.setBounds(230, 215, 70, 25);
        add(lblHotelId);

        txtHotelId = new JTextField("0");
        txtHotelId.setBounds(300, 215, 80, 25);
        add(txtHotelId);

        lblNights = new JLabel("Nights:");
        lblNights.setBounds(410, 215, 60, 25);
        add(lblNights);

        txtNights = new JTextField("1");
        txtNights.setBounds(470, 215, 60, 25);
        add(txtNights);

        lblNote = new JLabel("Use 0 for no flight or no hotel");
        lblNote.setBounds(560, 215, 250, 25);
        add(lblNote);

        btnBook = new JButton("Book");
        btnBook.setBounds(50, 255, 100, 25);
        add(btnBook);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(160, 255, 100, 25);
        add(btnCancel);

        btnInvoice = new JButton("Invoice");
        btnInvoice.setBounds(270, 255, 100, 25);
        add(btnInvoice);

        btnClear = new JButton("Clear");
        btnClear.setBounds(380, 255, 100, 25);
        add(btnClear);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(490, 255, 100, 25);
        add(btnRefresh);

        bookingList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(bookingList);
        scrollpane.setBounds(50, 295, 860, 235);
        add(scrollpane);

        flightList.addListSelectionListener(e -> {
            int Index = flightList.getSelectedIndex();
            if (!e.getValueIsAdjusting() && Index != -1 && Index < flightsShown.size()) {
                txtFlightId.setText(String.valueOf(flightsShown.get(Index).getFlightId()));
            }
        });
        hotelList.addListSelectionListener(e -> {
            int Index = hotelList.getSelectedIndex();
            if (!e.getValueIsAdjusting() && Index != -1 && Index < hotelsShown.length) {
                txtHotelId.setText(String.valueOf(hotelsShown[Index].getHotelId()));
            }
        });

        btnBook.addActionListener(this);
        btnCancel.addActionListener(this);
        btnInvoice.addActionListener(this);
        btnClear.addActionListener(this);
        btnRefresh.addActionListener(this);

        refreshOptions();
        refreshList();
    }

    private void refreshOptions() {
        flightModel.clear();
        flightsShown = TravelSystem.flights.getAllSortedByDeparture();
        for (int i = 0; i < flightsShown.size(); i++) {
            Flight f = flightsShown.get(i);
            flightModel.addElement("ID " + f.getFlightId() + " | " + f.getOrigin() + " to " + f.getDestination()
                    + " | " + f.getDeparture() + " | " + String.format("%.2f", f.getPrice()));
        }

        hotelModel.clear();
        hotelsShown = HotelDB.getAllHotels();
        for (int i = 0; i < hotelsShown.length; i++) {
            Hotel h = hotelsShown[i];
            hotelModel.addElement("ID " + h.getHotelId() + " | " + h.getName() + ", " + h.getLocation()
                    + " | " + String.format("%.2f", h.getPricePerNight()) + "/night | Rating " + h.getRating());
        }
    }

    private void refreshList() {
        listModel.clear();
        if (TravelSystem.currentUser == null) {
            return;
        }
        shown = TravelSystem.bookings.getHistory(TravelSystem.currentUser.getUserId());
        for (int i = 0; i < shown.size(); i++) {
            listModel.addElement(TravelSystem.bookings.describe(shown.get(i)));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBook) {
            String FlightId = txtFlightId.getText().trim();
            String HotelId = txtHotelId.getText().trim();
            String Nights = txtNights.getText().trim();

            if (TravelSystem.currentUser == null) {
                JOptionPane.showMessageDialog(this, "Please log in first", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (FlightId.isEmpty() || HotelId.isEmpty() || Nights.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all booking information", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int FlightValue;
            int HotelValue;
            int NightsValue;
            try {
                FlightValue = Integer.parseInt(FlightId);
                HotelValue = Integer.parseInt(HotelId);
                NightsValue = Integer.parseInt(Nights);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs and nights must be numbers", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (FlightValue < 0 || HotelValue < 0) {
                JOptionPane.showMessageDialog(this, "IDs cannot be negative", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (FlightValue == 0 && HotelValue == 0) {
                JOptionPane.showMessageDialog(this, "Choose at least a flight or a hotel", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (NightsValue < 1) {
                JOptionPane.showMessageDialog(this, "Nights must be at least 1", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Booking b = TravelSystem.bookings.book(TravelSystem.currentUser.getUserId(), FlightValue, HotelValue, NightsValue);
                refreshList();
                JOptionPane.showMessageDialog(this, "Booking successful! Total: " + String.format("%.2f", b.getTotalPrice()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnCancel) {
            int Index = bookingList.getSelectedIndex();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to cancel", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int Answer = JOptionPane.showConfirmDialog(this, "Cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (Answer != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                TravelSystem.bookings.cancel(shown.get(Index).getBookingId());
                refreshList();
                JOptionPane.showMessageDialog(this, "Booking cancelled");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnInvoice) {
            int Index = bookingList.getSelectedIndex();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new InvoiceFrame(shown.get(Index)).setVisible(true);
        }

        if (e.getSource() == btnClear) {
            txtFlightId.setText("0");
            txtHotelId.setText("0");
            txtNights.setText("1");
            flightList.clearSelection();
            hotelList.clearSelection();
            bookingList.clearSelection();
        }

        if (e.getSource() == btnRefresh) {
            refreshOptions();
            refreshList();
        }
    }
}
