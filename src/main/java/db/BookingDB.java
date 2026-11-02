package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import model.Booking;

public class BookingDB {

    // Saves a booking and returns the new booking ID. A flight or hotel ID of 0 is stored as NULL.
    public int addBooking(Booking b) throws SQLException {
        String sql = "INSERT INTO bookings (userId, flightId, hotelId, bookingDate, totalPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                throw new SQLException("No database connection");
            }
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, b.getUserId());
            if (b.getFlightId() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, b.getFlightId());
            }
            if (b.getHotelId() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, b.getHotelId());
            }
            ps.setString(4, b.getBookingDate());
            ps.setDouble(5, b.getTotalPrice());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM bookings WHERE bookingId = ?";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                throw new SQLException("No database connection");
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        }
    }

    public LinkedList<Booking> getAllBookings() throws SQLException {
        LinkedList<Booking> list = new LinkedList<>();
        String sql = "SELECT bookingId, userId, flightId, hotelId, "
                   + "DATE_FORMAT(bookingDate, '%Y-%m-%d %H:%i') AS bookingDate, totalPrice "
                   + "FROM bookings ORDER BY bookingId";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                throw new SQLException("No database connection");
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(readBooking(rs));
            }
        }
        return list;
    }

    // getInt returns 0 for NULL, which matches the "0 means none" convention.
    private Booking readBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("bookingId"),
                rs.getInt("userId"),
                rs.getInt("flightId"),
                rs.getInt("hotelId"),
                rs.getString("bookingDate"),
                rs.getDouble("totalPrice"));
    }
}
