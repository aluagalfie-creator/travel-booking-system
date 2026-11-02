/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Flight;

/**
 *
 * @author Alyanna
 */
public class FlightDB {
     public boolean addFlight(Flight f) {
        String sql = "INSERT INTO flights (departure, arrival, origin, destination, price) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, f.getDeparture());
            ps.setString(2, f.getArrival());
            ps.setString(3, f.getOrigin());
            ps.setString(4, f.getDestination());
            ps.setDouble(5, f.getPrice());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                f.setFlightId(keys.getInt(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("FlightDB addFlight error: " + e.getMessage());
            return false;
        }
    }
     
     public boolean updateFlight(Flight f) {
        String sql = "UPDATE flights SET departure = ?, arrival = ?, origin = ?, "
                   + "destination = ?, price = ? WHERE flightId = ?";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, f.getDeparture());
            ps.setString(2, f.getArrival());
            ps.setString(3, f.getOrigin());
            ps.setString(4, f.getDestination());
            ps.setDouble(5, f.getPrice());
            ps.setInt(6, f.getFlightId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("FlightDB updateFlight error: " + e.getMessage());
            return false;
        }
    }
     
     public boolean deleteFlight(int flightId) {
        String sql = "DELETE FROM flights WHERE flightId = ?";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, flightId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("FlightDB deleteFlight error: " + e.getMessage());
            return false;
        }
    }
     
     public ArrayList<Flight> getAllFlights() {
        ArrayList<Flight> list = new ArrayList<>();
        String sql = "SELECT flightId, "
                   + "DATE_FORMAT(departure, '%Y-%m-%d %H:%i') AS departure, "
                   + "DATE_FORMAT(arrival, '%Y-%m-%d %H:%i') AS arrival, "
                   + "origin, destination, price FROM flights";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return list;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Flight f = new Flight(
                    rs.getInt("flightId"),
                    rs.getString("departure"),
                    rs.getString("arrival"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getDouble("price")
                );
                list.add(f);
            }
        } catch (Exception e) {
            System.out.println("FlightDB getAllFlights error: " + e.getMessage());
        }
        return list;
    }
    
}
