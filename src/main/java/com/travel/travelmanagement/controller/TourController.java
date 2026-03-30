package com.travel.travelmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.travel.travelmanagement.entity.TourPackage;
import com.travel.travelmanagement.service.TourService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tours")
public class TourController {
	@RequestMapping("/tours")

	    @GetMapping
	    public String test() {
	        return "API WORKING";
	    }
	
    @Autowired
    private TourService tourService;

    @PostMapping
    public TourPackage addTour(@RequestBody TourPackage tour) {
        return tourService.addTour(tour);
    }

    @GetMapping
    public List<TourPackage> getTours() {
        return tourService.getTours();
    }
}