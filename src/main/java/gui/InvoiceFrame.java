package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InvoiceFrame extends JFrame implements ActionListener {
    private JLabel lblTitle, lblDetails;
    private JButton btnClose;
    private JList<String> invoiceList;
    private DefaultListModel<String> listModel;

    public InvoiceFrame(String bookingRecord) {
        setTitle("INVOICE");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();

        lblTitle = new JLabel("TRAVEL BOOKING INVOICE");
        lblTitle.setBounds(150, 10, 250, 30);
        add(lblTitle);

        lblDetails = new JLabel("Booking details:");
        lblDetails.setBounds(30, 50, 200, 30);
        add(lblDetails);

        listModel.addElement(bookingRecord);
        // TODO: add flight price, hotel price and the total

        invoiceList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(invoiceList);
        scrollpane.setBounds(30, 90, 420, 200);
        add(scrollpane);

        btnClose = new JButton("Close");
        btnClose.setBounds(180, 310, 100, 25);
        add(btnClose);

        btnClose.addActionListener(this);
    }

    InvoiceFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose) {
            dispose();
        }
    }
}
