package service;

import db.BookingDB;
import db.HotelDB;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import model.Booking;
import model.Flight;
import model.Hotel;

public class BookingService {
    private BookingDB bookingDB;
    private FlightCatalog flightCatalog;
    private LinkedList<Booking> bookings;

    public BookingService(BookingDB bookingDB, FlightCatalog flightCatalog) {
        this.bookingDB = bookingDB;
        this.flightCatalog = flightCatalog;
        this.bookings = new LinkedList<>();
    }

    // Called by TravelSystem.loadAll() on startup.
    public void loadAll() throws SQLException {
        bookings = bookingDB.getAllBookings();
    }

    // Use 0 for no flight or no hotel. One hotel night.
    public Booking book(int userId, int flightId, int hotelId) throws SQLException {
        return book(userId, flightId, hotelId, 1);
    }

    // Same as above, with the number of hotel nights.
    public Booking book(int userId, int flightId, int hotelId, int nights) throws SQLException {
        if (nights < 1) {
            throw new IllegalArgumentException("Nights must be at least 1");
        }
        if (flightId < 0 || hotelId < 0) {
            throw new IllegalArgumentException("IDs cannot be negative");
        }
        if (flightId == 0 && hotelId == 0) {
            throw new IllegalArgumentException("Choose at least a flight or a hotel");
        }

        double total = 0;
        if (flightId != 0) {
            Flight f = flightCatalog.getById(flightId);
            if (f == null) {
                throw new IllegalArgumentException("Flight ID " + flightId + " does not exist");
            }
            total += f.getPrice();
        }
        if (hotelId != 0) {
            Hotel h = HotelDB.getById(hotelId);
            if (h == null) {
                throw new IllegalArgumentException("Hotel ID " + hotelId + " does not exist");
            }
            total += h.getPricePerNight() * nights;
        }

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Booking b = new Booking(0, userId, flightId, hotelId, date, total);

        int newId = bookingDB.addBooking(b);
        if (newId == -1) {
            throw new SQLException("Booking was not saved");
        }
        b.setBookingId(newId);
        bookings.add(b);
        return b;
    }

    public boolean cancel(int bookingId) throws SQLException {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == bookingId) {
                bookingDB.deleteBooking(bookingId);
                bookings.remove(i);
                return true;
            }
        }
        return false;
    }

    public LinkedList<Booking> getHistory(int userId) {
        LinkedList<Booking> history = new LinkedList<>();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getUserId() == userId) {
                history.add(bookings.get(i));
            }
        }
        return history;
    }

    public LinkedList<Booking> getAllBookings() {
        return bookings;
    }

    // One line of text for the GUI lists.
    public String describe(Booking b) {
        String flight = "none";
        if (b.getFlightId() != 0) {
            Flight f = flightCatalog.getById(b.getFlightId());
            if (f == null) {
                flight = "#" + b.getFlightId() + " (removed)";
            } else {
                flight = f.getOrigin() + " to " + f.getDestination() + " (" + f.getDeparture() + ")";
            }
        }
        String hotel = "none";
        if (b.getHotelId() != 0) {
            Hotel h = HotelDB.getById(b.getHotelId());
            if (h == null) {
                hotel = "#" + b.getHotelId() + " (removed)";
            } else {
                hotel = h.getName() + ", " + h.getLocation();
            }
        }
        return "Booking #" + b.getBookingId()
                + " | " + b.getBookingDate()
                + " | Flight: " + flight
                + " | Hotel: " + hotel
                + " | Total: " + String.format("%.2f", b.getTotalPrice());
    }
}
