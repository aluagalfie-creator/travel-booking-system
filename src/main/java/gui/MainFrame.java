package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import service.TravelSystem;

public class MainFrame extends JFrame implements ActionListener {
    private JLabel lblWelcome;
    private JButton btnLogout;
    private JTabbedPane tabs;

    public MainFrame(String role) {
        setTitle("Travel Booking System - " + role);
        setSize(1000, 680);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String name = "User";
        if (TravelSystem.currentUser != null) {
            name = TravelSystem.currentUser.getName();
        }

        lblWelcome = new JLabel("Welcome, " + name);
        lblWelcome.setBounds(15, 10, 400, 28);
        add(lblWelcome);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(880, 10, 95, 28);
        add(btnLogout);

        tabs = new JTabbedPane();
        tabs.setBounds(10, 45, 965, 590);
        add(tabs);

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

        //Add to ActionListener
        btnLogout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogout) {
            TravelSystem.currentUser = null; //BE
            new LoginFrame().setVisible(true); //FE
            dispose();
        }
    }
}
