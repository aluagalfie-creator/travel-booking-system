CREATE DATABASE IF NOT EXISTS travel_booking_db;
USE travel_booking_db;

CREATE TABLE users (userId INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100),
  username VARCHAR(50) UNIQUE, password VARCHAR(50), role VARCHAR(10));
CREATE TABLE flights (flightId INT AUTO_INCREMENT PRIMARY KEY, departure DATETIME,
  arrival DATETIME, origin VARCHAR(50), destination VARCHAR(50), price DOUBLE);
CREATE TABLE hotels (hotelId INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100),
  location VARCHAR(100), rating DOUBLE, pricePerNight DOUBLE);
CREATE TABLE bookings (bookingId INT AUTO_INCREMENT PRIMARY KEY, userId INT,
  flightId INT NULL, hotelId INT NULL, bookingDate DATETIME, totalPrice DOUBLE,
  FOREIGN KEY (userId) REFERENCES users(userId));
INSERT INTO users (name, username, password, role) VALUES ('Admin','admin','admin123','ADMIN');