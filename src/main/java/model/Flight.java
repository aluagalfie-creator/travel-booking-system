package model;

public class Flight {
    private int flightId;
    private String departure, arrival, origin, destination;
    private double price;

    public Flight(int flightId, String departure, String arrival,
                  String origin, String destination, double price) {
        this.flightId = flightId;
        this.departure = departure;
        this.arrival = arrival;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }
    public String getDeparture() { return departure; }
    public void setDeparture(String departure) { this.departure = departure; }
    public String getArrival() { return arrival; }
    public void setArrival(String arrival) { this.arrival = arrival; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}