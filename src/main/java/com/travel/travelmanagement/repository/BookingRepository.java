package com.travel.travelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.travelmanagement.entity.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByEmail(String email);
}