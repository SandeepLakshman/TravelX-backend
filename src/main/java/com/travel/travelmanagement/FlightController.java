package com.travel.travelmanagement;

import com.travel.travelmanagement.dto.FlightData;
import com.travel.travelmanagement.dto.FlightResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    private final AviationStackService aviationService;

    public FlightController(AviationStackService aviationService) {
        this.aviationService = aviationService;
    }

    @GetMapping
    public ResponseEntity<?> getFlights(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "100") int limit) {
        
        Object data;
        if (from != null && to != null) {
            data = aviationService.getFlightsByRoute(from, to, date);
        } else {
            data = aviationService.getAllFlights(offset, limit);
        }
        return ResponseEntity.ok(Map.of("data", data));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getFlightsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(Map.of("data", aviationService.getFlightsByStatus(status)));
    }

    @GetMapping("/airline/{name}")
    public ResponseEntity<?> getFlightsByAirline(@PathVariable String name) {
        return ResponseEntity.ok(Map.of("data", aviationService.getFlightsByAirline(name)));
    }

    @GetMapping("/route")
    public ResponseEntity<?> getFlightsByRoute(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String date) {
        return ResponseEntity.ok(Map.of("data", aviationService.getFlightsByRoute(from, to, date)));
    }
}
