package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BookingPanel extends JPanel implements ActionListener {
    private JLabel lblTitle, lblUserId, lblFlightId, lblHotelId, lblNote;
    private JTextField txtUserId, txtFlightId, txtHotelId;
    private JButton btnBook, btnCancel, btnInvoice, btnClear;
    private JList<String> bookingList;
    private DefaultListModel<String> listModel;

    public BookingPanel() {
        setLayout(null);
        listModel = new DefaultListModel<>();

        lblTitle = new JLabel("BOOK A TRIP");
        lblTitle.setBounds(300, 10, 250, 30);
        add(lblTitle);

        lblUserId = new JLabel("User ID:");
        lblUserId.setBounds(50, 50, 100, 30);
        add(lblUserId);

        lblFlightId = new JLabel("Flight ID:");
        lblFlightId.setBounds(50, 90, 100, 30);
        add(lblFlightId);

        lblHotelId = new JLabel("Hotel ID:");
        lblHotelId.setBounds(50, 130, 100, 30);
        add(lblHotelId);

        lblNote = new JLabel("Use 0 for no flight or no hotel");
        lblNote.setBounds(360, 90, 250, 30);
        add(lblNote);

        txtUserId = new JTextField();
        txtUserId.setBounds(150, 55, 200, 25);
        add(txtUserId);

        txtFlightId = new JTextField("0");
        txtFlightId.setBounds(150, 95, 200, 25);
        add(txtFlightId);

        txtHotelId = new JTextField("0");
        txtHotelId.setBounds(150, 135, 200, 25);
        add(txtHotelId);

        btnBook = new JButton("Book");
        btnBook.setBounds(50, 180, 100, 25);
        add(btnBook);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(160, 180, 100, 25);
        add(btnCancel);

        btnInvoice = new JButton("Invoice");
        btnInvoice.setBounds(270, 180, 100, 25);
        add(btnInvoice);

        btnClear = new JButton("Clear");
        btnClear.setBounds(380, 180, 100, 25);
        add(btnClear);

        bookingList = new JList<String>(listModel);
        JScrollPane scrollpane = new JScrollPane(bookingList);
        scrollpane.setBounds(50, 230, 640, 270);
        add(scrollpane);

        btnBook.addActionListener(this);
        btnCancel.addActionListener(this);
        btnInvoice.addActionListener(this);
        btnClear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBook) {
            String UserId = txtUserId.getText();
            String FlightId = txtFlightId.getText();
            String HotelId = txtHotelId.getText();

            if (UserId.isEmpty() || FlightId.isEmpty() || HotelId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all booking information", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Integer.parseInt(UserId);
                int FlightValue = Integer.parseInt(FlightId);
                int HotelValue = Integer.parseInt(HotelId);
                if (FlightValue < 0 || HotelValue < 0) {
                    JOptionPane.showMessageDialog(this, "IDs cannot be negative", "Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (FlightValue == 0 && HotelValue == 0) {
                    JOptionPane.showMessageDialog(this, "Choose at least a flight or a hotel", "Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs must be numbers", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: BookingService.book(userId, flightId, hotelId)
            String Record = "User: " + UserId
                    + " | Flight: " + FlightId
                    + " | Hotel: " + HotelId;
            listModel.addElement(Record);
            JOptionPane.showMessageDialog(this, "Booking successful!");
        }

        if (e.getSource() == btnCancel) {
            int Index = bookingList.getSelectedIndex();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to cancel", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // TODO: BookingService.cancel(int bookingId)
            listModel.remove(Index);
        }

        if (e.getSource() == btnInvoice) {
            int Index = bookingList.getSelectedIndex();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking", "Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new InvoiceFrame(listModel.get(Index)).setVisible(true);
        }

        if (e.getSource() == btnClear) {
            txtUserId.setText("");
            txtFlightId.setText("0");
            txtHotelId.setText("0");
        }
    }
}
