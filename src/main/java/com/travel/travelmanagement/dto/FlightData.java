package com.travel.travelmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightData {
    @JsonProperty("flight_date")
    private String flightDate;
    @JsonProperty("flight_status")
    private String flightStatus;
    private Departure departure;
    private Arrival arrival;
    private Airline airline;
    private FlightInfo flight;

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public FlightInfo getFlight() {
        return flight;
    }

    public void setFlight(FlightInfo flight) {
        this.flight = flight;
    }
}
