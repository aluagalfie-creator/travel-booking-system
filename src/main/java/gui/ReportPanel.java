package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ReportPanel extends JPanel implements ActionListener {
    private JLabel lblTitle, lblTotal;
    private JButton btnLoad, btnClear;
    private JList<String> reportList;
    private DefaultListModel<String> listModel;

    public ReportPanel() {
        setLayout(null);
        listModel = new DefaultListModel<>();

        lblTitle = new JLabel("ALL BOOKINGS REPORT");
        lblTitle.setBounds(280, 10, 250, 30);
        add(lblTitle);

        btnLoad = new JButton("Load All");
        btnLoad.setBounds(50, 55, 100, 25);
        add(btnLoad);

        btnClear = new JButton("Clear");
        btnClear.setBounds(160, 55, 100, 25);
        add(btnClear);

        lblTotal = new JLabel("Total bookings: 0");
        lblTotal.setBounds(300, 50, 200, 30);
        add(lblTotal);

        reportList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(reportList);
        scrollpane.setBounds(50, 110, 640, 390);
        add(scrollpane);

        btnLoad.addActionListener(this);
        btnClear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoad) {
            listModel.clear();
            // TODO: BookingService.getAllBookings(), then listModel.addElement(...) for each booking
            lblTotal.setText("Total bookings: " + listModel.getSize());
        }

        if (e.getSource() == btnClear) {
            listModel.clear();
            lblTotal.setText("Total bookings: 0");
        }
    }
}
