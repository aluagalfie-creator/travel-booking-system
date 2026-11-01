package db;

import ds.LinkedList;
import model.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelDB {

    public static boolean addHotel(Hotel h) {
        String sql = "INSERT INTO hotels (name, location, pricePerNight, rating) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, h.getName());
            ps.setString(2, h.getLocation());
            ps.setDouble(3, h.getPricePerNight());
            ps.setDouble(4, h.getRating());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                h.setHotelId(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateHotel(Hotel h) {
        String sql = "UPDATE hotels SET name=?, location=?, pricePerNight=?, rating=? WHERE hotelId=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, h.getName());
            ps.setString(2, h.getLocation());
            ps.setDouble(3, h.getPricePerNight());
            ps.setDouble(4, h.getRating());
            ps.setInt(5, h.getHotelId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteHotel(int hotelId) {
        String sql = "DELETE FROM hotels WHERE hotelId=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // for example, a booking still references this hotel
            return false;
        }
    }

    public static Hotel[] getAllHotels() {
        LinkedList list = new LinkedList();
        String sql = "SELECT * FROM hotels ORDER BY hotelId";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(readHotel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Hotel[] result = new Hotel[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (Hotel) list.get(i);
        }
        return result;
    }

    public static Hotel getById(int hotelId) {
        String sql = "SELECT * FROM hotels WHERE hotelId=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return readHotel(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Hotel readHotel(ResultSet rs) throws SQLException {
        Hotel h = new Hotel(0, "","",0,0);
        h.setHotelId(rs.getInt("hotelId"));
        h.setName(rs.getString("name"));
        h.setLocation(rs.getString("location"));
        h.setPricePerNight(rs.getDouble("pricePerNight"));
        h.setRating(rs.getDouble("rating"));
        return h;
    }
}
