package gui;

import javax.swing.*;
import service.TravelSystem;

public class MainFrame extends JFrame {
    private JLabel lblWelcome = new JLabel();
    private JButton btnLogout = new JButton("Logout");
    private JTabbedPane tabs = new JTabbedPane();

    public MainFrame(String role) {
        setTitle("Travel Booking System - " + role);
        setSize(1000, 680);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String name = "User";
        if (TravelSystem.currentUser != null) {
            name = TravelSystem.currentUser.getName();
        }
        lblWelcome.setText("Welcome, " + name);
        lblWelcome.setBounds(15, 10, 400, 28);
        btnLogout.setBounds(880, 10, 95, 28);
        tabs.setBounds(10, 45, 965, 590);

        if (role.equals("ADMIN")) {
            tabs.addTab("Flights", new FlightPanel(TravelSystem.flights));
            tabs.addTab("Hotels", new HotelPanel());
            tabs.addTab("Packages", new PackagePanel());
            tabs.addTab("Customers", new CustomerPanel());
            tabs.addTab("Reports", new ReportPanel());
        } else {
            tabs.addTab("Packages", new PackagePanel());
            tabs.addTab("Book", new BookingPanel());
            tabs.addTab("My Bookings", new HistoryPanel());
        }

        btnLogout.addActionListener(e -> logout());

        add(lblWelcome);
        add(btnLogout);
        add(tabs);
    }

    private void logout() {
        TravelSystem.currentUser = null;
        new LoginFrame().setVisible(true);
        dispose();
    }
}
