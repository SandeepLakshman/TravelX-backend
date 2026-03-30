package com.travel.travelmanagement.controller;

import com.travel.travelmanagement.dto.BookingRequest;
import com.travel.travelmanagement.entity.Booking;
import com.travel.travelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            System.out.println("Processing Booking for: " + request.getEmail() + " | Flight: " + request.getFlightNumber());
            return ResponseEntity.status(201).body(bookingService.saveFlightBooking(request));
        } catch (Exception e) {
            System.err.println("CRITICAL BOOKING ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid booking data: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(org.springframework.security.core.Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(bookingService.getBookingsByEmail(email));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/tours")
    public List<String> getTours() {
        return Arrays.asList("City Tour", "Mountain Hike", "Beach Relaxation");
    }

    @PatchMapping("/admin/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id, org.springframework.security.core.Authentication authentication) {
        try {
            bookingService.cancelBookingByUser(id, authentication.getName());
            return ResponseEntity.ok().body(Map.of("message", "Booking cancelled successfully"));
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cancellation failed: " + e.getMessage());
        }
    }
}