package com.travel.travelmanagement.service;

import com.travel.travelmanagement.dto.BookingRequest;
import com.travel.travelmanagement.entity.Booking;
import com.travel.travelmanagement.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private static final int CANCELLATION_WINDOW_HOURS = 12;

    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveFlightBooking(BookingRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required for booking");
        }
        if (request.getTourTitle() == null || request.getTourTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tour Title is required for booking");
        }

        Booking booking = new Booking();
        booking.setName(request.getName());
        booking.setEmail(request.getEmail());
        booking.setDate(request.getDate());
        booking.setPersons(request.getPersons());
        booking.setFlightNumber(request.getFlightNumber());
        booking.setAirline(request.getAirline());
        booking.setDepartureIata(request.getDepartureIata());
        booking.setArrivalIata(request.getArrivalIata());
        booking.setStatus("CONFIRMED");
        booking.setTourTitle(request.getTourTitle());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setPassengersData(request.getPassengersData());
        return bookingRepository.save(booking);
    }

    public Booking updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    /**
     * User self-cancellation – only allowed within the configured time window.
     * Admins should use updateBookingStatus() directly.
     */
    public void cancelBookingByUser(Long id, String userEmail) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        if (!booking.getEmail().equalsIgnoreCase(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this booking");
        }

        try {
            // Parse flight date
            String dateStr = booking.getDate();
            LocalDateTime flightDateTime;
            if (dateStr != null && dateStr.contains("T")) {
                // e.g. 2024-04-10T08:00:00
                flightDateTime = LocalDateTime.parse(dateStr.substring(0, 19)); // Strip timezone if present or rely on standard format
            } else if (dateStr != null && !dateStr.isEmpty()) {
                // e.g. 2024-04-10
                flightDateTime = LocalDateTime.parse(dateStr + "T00:00:00");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid flight date in booking");
            }

            LocalDateTime cutoff = flightDateTime.minusHours(CANCELLATION_WINDOW_HOURS);
            if (LocalDateTime.now().isAfter(cutoff)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "Cancellations are only allowed up to " + CANCELLATION_WINDOW_HOURS + " hours before departure.");
            }
        } catch (Exception e) {
            if (e instanceof ResponseStatusException) {
                throw e;
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not verify cancellation time window: " + e.getMessage());
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByEmail(email);
    }
}