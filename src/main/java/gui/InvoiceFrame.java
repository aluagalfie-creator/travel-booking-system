package gui;

import db.HotelDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.Booking;
import model.Flight;
import model.Hotel;
import service.TravelSystem;

public class InvoiceFrame extends JFrame implements ActionListener {
    private JLabel lblTitle, lblDetails;
    private JButton btnClose;
    private JList<String> invoiceList;
    private DefaultListModel<String> listModel;

    public InvoiceFrame(Booking booking) {
        setTitle("INVOICE");
        setSize(500, 520);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();

        lblTitle = new JLabel("TRAVEL BOOKING INVOICE");
        lblTitle.setBounds(150, 10, 250, 30);
        add(lblTitle);

        lblDetails = new JLabel("Booking details:");
        lblDetails.setBounds(30, 50, 200, 30);
        add(lblDetails);

        listModel.addElement("Booking ID: " + booking.getBookingId());
        listModel.addElement("Date: " + booking.getBookingDate());
        if (TravelSystem.currentUser != null && TravelSystem.currentUser.getUserId() == booking.getUserId()) {
            listModel.addElement("Customer: " + TravelSystem.currentUser.getName());
        } else {
            listModel.addElement("Customer ID: " + booking.getUserId());
        }
        listModel.addElement(" ");

        double FlightPart = 0;
        listModel.addElement("FLIGHT");
        if (booking.getFlightId() == 0) {
            listModel.addElement("   none");
        } else {
            Flight f = TravelSystem.flights.getById(booking.getFlightId());
            if (f != null) {
                FlightPart = f.getPrice();
            }
            if (f == null) {
                listModel.addElement("   Flight #" + booking.getFlightId() + " (removed)");
            } else {
                listModel.addElement("   " + f.getOrigin() + " to " + f.getDestination());
                listModel.addElement("   Departure: " + f.getDeparture());
                listModel.addElement("   Arrival: " + f.getArrival());
                listModel.addElement("   Price: " + String.format("%.2f", f.getPrice()));
            }
        }
        listModel.addElement(" ");

        listModel.addElement("HOTEL");
        if (booking.getHotelId() == 0) {
            listModel.addElement("   none");
        } else {
            Hotel h = HotelDB.getById(booking.getHotelId());
            if (h == null) {
                listModel.addElement("   Hotel #" + booking.getHotelId() + " (removed)");
            } else {
                listModel.addElement("   " + h.getName() + ", " + h.getLocation());
                listModel.addElement("   Rating: " + h.getRating());
                int Nights = 1;
                if (h.getPricePerNight() > 0) {
                    Nights = (int) Math.round((booking.getTotalPrice() - FlightPart) / h.getPricePerNight());
                }
                if (Nights < 1) {
                    Nights = 1;
                }
                listModel.addElement("   Price per night: " + String.format("%.2f", h.getPricePerNight()));
                listModel.addElement("   Nights: " + Nights);
                listModel.addElement("   Hotel total: " + String.format("%.2f", h.getPricePerNight() * Nights));
            }
        }
        listModel.addElement(" ");
        listModel.addElement("TOTAL: " + String.format("%.2f", booking.getTotalPrice()));

        invoiceList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(invoiceList);
        scrollpane.setBounds(30, 90, 420, 330);
        add(scrollpane);

        btnClose = new JButton("Close");
        btnClose.setBounds(180, 440, 100, 25);
        add(btnClose);

        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose) {
            dispose();
        }
    }
}
