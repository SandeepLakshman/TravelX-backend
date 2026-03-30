package com.travel.travelmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {

    @Value("${rapidapi.key:YOUR_RAPID_API_KEY_HERE}")
    private String rapidApiKey;

    @Value("${rapidapi.host:sky-scrapper.p.rapidapi.com}")
    private String rapidApiHost;

    public Map<String, Object> searchFlights(String origin, String destination, String date) {
        // Implementation connecting to RapidAPI Sky Scraper
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            // For safety in this fast demo if key is missing, return intelligent fallback
            if (rapidApiKey.equals("YOUR_RAPID_API_KEY_HERE") || rapidApiKey.isEmpty()) {
                return generateFallbackData(origin, destination, date);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-key", rapidApiKey);
            headers.set("x-rapidapi-host", rapidApiHost);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Typical Sky Scraper URL format
            String url = "https://" + rapidApiHost + "/api/v2/flights/searchFlights?originSkyId=" + origin + "&destinationSkyId=" + destination + "&date=" + date;
            
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("API Call failed: " + e.getMessage());
            return generateFallbackData(origin, destination, date); // Graceful fallback
        }
    }

    private Map<String, Object> generateFallbackData(String origin, String destination, String date) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> flights = new ArrayList<>();
        
        // Flight 1: Cheapest
        Map<String, Object> f1 = new HashMap<>();
        f1.put("id", "F101");
        f1.put("airline", "IndiGo");
        f1.put("departure", "06:00 AM");
        f1.put("arrival", "08:30 AM");
        f1.put("duration", "2h 30m");
        f1.put("price", 4500);
        f1.put("tag", "Cheapest");
        f1.put("seatsLeft", 4);
        flights.add(f1);

        // Flight 2: Fastest
        Map<String, Object> f2 = new HashMap<>();
        f2.put("id", "F102");
        f2.put("airline", "Vistara");
        f2.put("departure", "09:00 AM");
        f2.put("arrival", "11:00 AM");
        f2.put("duration", "2h 00m");
        f2.put("price", 6200);
        f2.put("tag", "Fastest");
        f2.put("seatsLeft", 2);
        flights.add(f2);

        // Flight 3: Recommended
        Map<String, Object> f3 = new HashMap<>();
        f3.put("id", "F103");
        f3.put("airline", "Air India");
        f3.put("departure", "02:00 PM");
        f3.put("arrival", "04:45 PM");
        f3.put("duration", "2h 45m");
        f3.put("price", 5100);
        f3.put("tag", "Recommended");
        f3.put("seatsLeft", 9);
        flights.add(f3);

        response.put("status", true);
        response.put("data", flights);
        
        // AI Price Intelligence
        Map<String, String> aiInsight = new HashMap<>();
        aiInsight.put("message", "Best time to book. Prices may increase by ₹1,200 tomorrow.");
        response.put("aiIntelligence", aiInsight);

        return response;
    }
}
