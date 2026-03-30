package com.travel.travelmanagement.entity;

public class Flight {

    private String airline;
    private String departure;
    private String arrival;
    private int duration;
    private int price;
    private String from;
    private String to;

    // ✅ IMPORTANT: Default constructor
    public Flight() {}

    // ✅ Parameterized constructor
    public Flight(String airline, String departure, String arrival,
                  int duration, int price, String from, String to) {
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.price = price;
        this.from = from;
        this.to = to;
    }

    // ✅ Getters
    public String getAirline() { return airline; }
    public String getDeparture() { return departure; }
    public String getArrival() { return arrival; }
    public int getDuration() { return duration; }
    public int getPrice() { return price; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
}