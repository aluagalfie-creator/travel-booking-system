package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;
import model.Booking;
import service.TravelSystem;

public class ReportPanel extends JPanel implements ActionListener {
    private JLabel lblTitle, lblTotal;
    private JButton btnLoad, btnClear;
    private JList<String> reportList;
    private DefaultListModel<String> listModel;

    public ReportPanel() {
        setLayout(null);
        listModel = new DefaultListModel<>();

        lblTitle = new JLabel("ALL BOOKINGS REPORT");
        lblTitle.setBounds(400, 10, 250, 30);
        add(lblTitle);

        btnLoad = new JButton("Load All");
        btnLoad.setBounds(50, 55, 100, 25);
        add(btnLoad);

        btnClear = new JButton("Clear");
        btnClear.setBounds(160, 55, 100, 25);
        add(btnClear);

        lblTotal = new JLabel("Total bookings: 0 | Revenue: 0.00");
        lblTotal.setBounds(300, 50, 400, 30);
        add(lblTotal);

        reportList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(reportList);
        scrollpane.setBounds(50, 110, 860, 420);
        add(scrollpane);

        btnLoad.addActionListener(this);
        btnClear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoad) {
            listModel.clear();
            LinkedList<Booking> all = TravelSystem.bookings.getAllBookings();
            double Revenue = 0;
            for (int i = 0; i < all.size(); i++) {
                listModel.addElement("User #" + all.get(i).getUserId() + " | "
                        + TravelSystem.bookings.describe(all.get(i)));
                Revenue = Revenue + all.get(i).getTotalPrice();
            }
            lblTotal.setText("Total bookings: " + listModel.getSize()
                    + " | Revenue: " + String.format("%.2f", Revenue));
        }

        if (e.getSource() == btnClear) {
            listModel.clear();
            lblTotal.setText("Total bookings: 0 | Revenue: 0.00");
        }
    }
}
