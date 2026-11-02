/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import db.FlightDB;
import ds.FlightBST;
import java.util.ArrayList;
import model.Flight;

/**
 *
 * @author Alyanna
 */
public class FlightCatalog {
     private FlightBST tree = new FlightBST();
    private FlightDB db = new FlightDB();

    public void loadFromDatabase() {
        tree.clear();
        ArrayList<Flight> flights = db.getAllFlights();
        for (Flight f : flights) {
            tree.insert(f);
        }
    }

    public boolean addFlight(Flight f) {
        if (!db.addFlight(f)) {
            return false;
        }
        tree.insert(f);
        return true;
    }

    public boolean updateFlight(Flight f) {
        if (!db.updateFlight(f)) {
            return false;
        }
        tree.delete(f.getFlightId());
        tree.insert(f);
        return true;
    }

    public boolean deleteFlight(int flightId) {
        if (!db.deleteFlight(flightId)) {
            return false;
        }
        tree.delete(flightId);
        return true;
    }

    public ArrayList<Flight> searchByDestination(String destination) {
        return tree.searchByDestination(destination);
    }

    public ArrayList<Flight> getAllSortedByDeparture() {
        return tree.getAllSorted();
    }

    public Flight getById(int flightId) {
        return tree.searchById(flightId);
    }
    
}
