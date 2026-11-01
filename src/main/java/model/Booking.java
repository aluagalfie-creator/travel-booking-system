package model;

public class Booking {
    private int bookingId, userId, flightId, hotelId;
    private String bookingDate;
    private double totalPrice;

    public Booking(int bookingId, int userId, int flightId, int hotelId,
                   String bookingDate, double totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.hotelId = hotelId;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}