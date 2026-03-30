package com.travel.travelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.travelmanagement.entity.TourPackage;

public interface TourRepository extends JpaRepository<TourPackage, Long> {

}