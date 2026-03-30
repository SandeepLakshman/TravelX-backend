package com.travel.travelmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String date;
    private int persons;
    private String flightNumber;
    private String airline;
    private String departureIata;
    private String arrivalIata;
    private String status;
    private String tourTitle;
    private Double totalPrice;
    
    @Column(columnDefinition = "TEXT")
    private String passengersData;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Booking() {}

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getPersons() { return persons; }
    public void setPersons(int persons) { this.persons = persons; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getDepartureIata() { return departureIata; }
    public void setDepartureIata(String departureIata) { this.departureIata = departureIata; }

    public String getArrivalIata() { return arrivalIata; }
    public void setArrivalIata(String arrivalIata) { this.arrivalIata = arrivalIata; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTourTitle() { return tourTitle; }
    public void setTourTitle(String tourTitle) { this.tourTitle = tourTitle; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getPassengersData() { return passengersData; }
    public void setPassengersData(String passengersData) { this.passengersData = passengersData; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}