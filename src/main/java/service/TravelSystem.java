package service;

import model.User;

public class TravelSystem {
    public static FlightCatalog flights = new FlightCatalog();
    // Add after Cedie's fix is merged: CustomerRegistry customers
    // Add after Cristofer's PR is merged: BookingService bookings

    // The logged-in user (null if nobody)
    public static User currentUser = null;

    public static void loadAll() {
    flights.loadFromDatabase();
}
}