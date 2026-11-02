import javax.swing.*;
import java.awt.*;

public class CustomerPanel extends JPanel {
    private JLabel titleLabel, welcomeLabel;

    public CustomerPanel(String loggedInUser) {
        // Absolute positioning layout
        setLayout(null);
        setBounds(0, 0, 450, 400);

        titleLabel = new JLabel("Travel Booking - Customer Dashboard");
        titleLabel.setBounds(30, 30, 380, 30);
        add(titleLabel);

        welcomeLabel = new JLabel("Welcome back, " + loggedInUser + "!");
        welcomeLabel.setBounds(30, 80, 380, 25);
        add(welcomeLabel);

    }
}
