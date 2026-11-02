package model;

public class PackageDeal {
    private int id;
    private String name;
    private Flight flight;
    private Hotel hotel;
    private int nights;
    private double totalPrice;
    private double rating;

    public PackageDeal(int id, String name, Flight flight, Hotel hotel, int nights) {
        this.id = id;
        this.name = name;
        this.flight = flight;
        this.hotel = hotel;
        this.nights = nights;
        this.totalPrice = flight.getPrice() + (hotel.getPricePerNight() * nights);
        this.rating = hotel.getRating();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Flight getFlight() {
        return flight;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getRating() {
        return rating;
    }
}
