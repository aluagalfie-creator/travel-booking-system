package service;

import db.BookingDB;
import model.User;

public class TravelSystem {
    public static FlightCatalog flights = new FlightCatalog();
    public static CustomerRegistry customers = new CustomerRegistry();
    public static BookingService bookings = new BookingService(new BookingDB(), flights);

    // The logged-in user (null if nobody)
    public static User currentUser = null;

    public static void loadAll() {
        flights.loadFromDatabase();
        customers.loadFromDatabase();
        try {
            bookings.loadAll();
        } catch (java.sql.SQLException e) {
            System.out.println("Load bookings error: " + e.getMessage());
        }
    }
}
