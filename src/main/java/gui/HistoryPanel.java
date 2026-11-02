package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;
import model.Booking;
import service.TravelSystem;

public class HistoryPanel extends JPanel implements ActionListener {
    private JLabel lblTitle, lblUser;
    private JButton btnRefresh, btnInvoice;
    private JList<String> historyList;
    private DefaultListModel<String> listModel;
    private LinkedList<Booking> shown;

    public HistoryPanel() {
        setLayout(null);
        listModel = new DefaultListModel<>();
        shown = new LinkedList<>();

        lblTitle = new JLabel("BOOKING HISTORY");
        lblTitle.setBounds(400, 10, 250, 30);
        add(lblTitle);

        String UserName = "nobody";
        if (TravelSystem.currentUser != null) {
            UserName = TravelSystem.currentUser.getName();
        }
        lblUser = new JLabel("Bookings of: " + UserName);
        lblUser.setBounds(50, 50, 300, 30);
        add(lblUser);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(370, 55, 100, 25);
        add(btnRefresh);

        btnInvoice = new JButton("Invoice");
        btnInvoice.setBounds(480, 55, 100, 25);
        add(btnInvoice);

        historyList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(historyList);
        scrollpane.setBounds(50, 110, 860, 420);
        add(scrollpane);

        btnRefresh.addActionListener(this);
        btnInvoice.addActionListener(this);

        refreshList();
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
        if (e.getSource() == btnRefresh) {
            if (TravelSystem.currentUser == null) {
                JOptionPane.showMessageDialog(this, "Please log in first", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            refreshList();
        }

        if (e.getSource() == btnInvoice) {
            int Index = historyList.getSelectedIndex();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new InvoiceFrame(shown.get(Index)).setVisible(true);
        }
    }
}
