/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds;

import java.util.ArrayList;
import model.Flight;

/**
 *
 * @author Alyanna
 */
public class FlightBST {
    private static class Node {
        Flight flight;
        Node left, right;

        Node(Flight flight) {
            this.flight = flight;
        }
    }

    private Node root;
    
    public void insert(Flight f) {
        root = insert(root, f);
    }

    private Node insert(Node node, Flight f) {
        if (node == null) {
            return new Node(f);
        }
        if (f.getDeparture().compareTo(node.flight.getDeparture()) < 0) {
            node.left = insert(node.left, f);
        } else {
            node.right = insert(node.right, f);
        }
        return node;
    }
    
     public Flight searchById(int flightId) {
        return searchById(root, flightId);
    }

    private Flight searchById(Node node, int flightId) {
        if (node == null) {
            return null;
        }
        if (node.flight.getFlightId() == flightId) {
            return node.flight;
        }
        Flight found = searchById(node.left, flightId);
        if (found != null) {
            return found;
        }
        return searchById(node.right, flightId);
    }
    
     public ArrayList<Flight> searchByDestination(String destination) {
        ArrayList<Flight> results = new ArrayList<>();
        searchByDestination(root, destination, results);
        return results;
    }

    private void searchByDestination(Node node, String destination, ArrayList<Flight> results) {
        if (node == null) {
            return;
        }
        searchByDestination(node.left, destination, results);
        if (node.flight.getDestination().equalsIgnoreCase(destination)) {
            results.add(node.flight);
        }
        searchByDestination(node.right, destination, results);
    }
    
    public ArrayList<Flight> getAllSorted() {
        ArrayList<Flight> list = new ArrayList<>();
        inOrder(root, list);
        return list;
    }

    private void inOrder(Node node, ArrayList<Flight> list) {
        if (node == null) {
            return;
        }
        inOrder(node.left, list);
        list.add(node.flight);
        inOrder(node.right, list);
    }
    
     public boolean delete(int flightId) {
        Flight target = searchById(flightId);
        if (target == null) {
            return false;
        }
        root = delete(root, target);
        return true;
    }

    private Node delete(Node node, Flight target) {
        if (node == null) {
            return null;
        }
        int cmp = target.getDeparture().compareTo(node.flight.getDeparture());

        if (cmp < 0) {
            node.left = delete(node.left, target);
        } else if (cmp > 0) {
            node.right = delete(node.right, target);
        } else if (node.flight.getFlightId() != target.getFlightId()) {
                 node.right = delete(node.right, target);
        } else {
            if (node.left == null) {
                return node.right;         
            }
            if (node.right == null) {
                return node.left;         
            }
            Node smallest = node.right;
            while (smallest.left != null) {
                smallest = smallest.left;
            }
            node.flight = smallest.flight;
            node.right = delete(node.right, smallest.flight);
        }
        return node;
    }
    
     public void clear() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
