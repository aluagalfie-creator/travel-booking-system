package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HistoryPanel extends JPanel implements ActionListener {
    private JLabel lblTitle, lblUserId;
    private JTextField txtUserId;
    private JButton btnLoad, btnInvoice;
    private JList<String> historyList;
    private DefaultListModel<String> listModel;

    public HistoryPanel() {
        setLayout(null);
        listModel = new DefaultListModel<>();

        lblTitle = new JLabel("BOOKING HISTORY");
        lblTitle.setBounds(280, 10, 250, 30);
        add(lblTitle);

        lblUserId = new JLabel("User ID:");
        lblUserId.setBounds(50, 50, 100, 30);
        add(lblUserId);

        txtUserId = new JTextField();
        txtUserId.setBounds(150, 55, 200, 25);
        add(txtUserId);

        btnLoad = new JButton("Load");
        btnLoad.setBounds(370, 55, 100, 25);
        add(btnLoad);

        btnInvoice = new JButton("Invoice");
        btnInvoice.setBounds(480, 55, 100, 25);
        add(btnInvoice);

        historyList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(historyList);
        scrollpane.setBounds(50, 110, 640, 390);
        add(scrollpane);

        btnLoad.addActionListener(this);
        btnInvoice.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoad) {
            String UserId = txtUserId.getText();

            if (UserId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a user ID", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Integer.parseInt(UserId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "User ID must be a number", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            listModel.clear();
            // TODO: BookingService.getHistory(userId), then listModel.addElement(...) for each booking
        }

        if (e.getSource() == btnInvoice) {
            int Index = historyList.getSelectedIndex();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new InvoiceFrame(listModel.get(Index)).setVisible(true);
        }
    }
}
