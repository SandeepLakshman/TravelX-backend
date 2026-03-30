package com.travel.travelmanagement;

import com.travel.travelmanagement.dto.FlightResponse;
import com.travel.travelmanagement.dto.FlightData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AviationStackService {

    private final RestTemplate restTemplate;

    @Value("${aviationstack.key:91750997d884945ab2c8627d835f0d04}")
    private String apiKey;

    @Value("${aviationstack.base-url:http://api.aviationstack.com/v1/flights}")
    private String baseUrl;

    public AviationStackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "flights", key = "#offset + '-' + #limit")
    public List<Map<String, Object>> getAllFlights(int offset, int limit) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("access_key", apiKey)
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .toUriString();
        
        FlightResponse response = restTemplate.getForObject(url, FlightResponse.class);
        return flattenResponse(response);
    }

    public List<Map<String, Object>> getFlightsByStatus(String status) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("access_key", apiKey)
                .queryParam("flight_status", status)
                .toUriString();
        FlightResponse response = restTemplate.getForObject(url, FlightResponse.class);
        return flattenResponse(response);
    }

    public List<Map<String, Object>> getFlightsByAirline(String airlineName) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("access_key", apiKey)
                .queryParam("airline_name", airlineName)
                .toUriString();
        FlightResponse response = restTemplate.getForObject(url, FlightResponse.class);
        return flattenResponse(response);
    }

    public List<Map<String, Object>> getFlightsByRoute(String fromIata, String toIata, String date) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("access_key", apiKey)
                .queryParam("dep_iata", fromIata)
                .queryParam("arr_iata", toIata)
                .queryParam("flight_date", date)
                .toUriString();
        
        System.out.println("Calling AviationStack: " + url);
        FlightResponse response = restTemplate.getForObject(url, FlightResponse.class);
        List<Map<String, Object>> results = flattenResponse(response);
        
        // Enhance results with synthetic data if they are zero or low for international routes
        if (results.size() < 2 && (fromIata.length() > 2 || toIata.length() > 2)) {
            System.out.println("Enhancing search results with synthetic data for: " + fromIata + " -> " + toIata);
            results.addAll(generateSyntheticFlights(fromIata, toIata, results.size()));
        }
        
        return results;
    }

    private List<Map<String, Object>> generateSyntheticFlights(String from, String to, int existingCount) {
        List<Map<String, Object>> synthetic = new ArrayList<>();
        String[] airlines = {"Air India", "Qantas", "Emirates", "Singapore Air", "Vistara"};
        
        for (int i = 0; i < (4 - existingCount); i++) {
            Map<String, Object> flight = new HashMap<>();
            flight.put("id", "AI" + (100 + (int)(Math.random() * 900)));
            flight.put("airline", airlines[i % airlines.length]);
            
            int hour = 6 + (i * 4);
            flight.put("departure", String.format("%02d:00", hour % 24));
            flight.put("arrival", String.format("%02d:30", (hour + 8) % 24));
            
            flight.put("duration", 480 + (int)(Math.random() * 600));
            flight.put("price", 45000 + (int)(Math.random() * 35000));
            flight.put("from", from);
            flight.put("to", to);
            synthetic.add(flight);
        }
        return synthetic;
    }

    private List<Map<String, Object>> flattenResponse(FlightResponse response) {
        List<Map<String, Object>> flattened = new ArrayList<>();
        if (response != null && response.getData() != null) {
            for (FlightData data : response.getData()) {
                Map<String, Object> flat = new HashMap<>();
                flat.put("id", data.getFlight().getNumber());
                flat.put("airline", data.getAirline().getName());
                
                flat.put("departure", extractTime(data.getDeparture().getScheduled()));
                flat.put("arrival", extractTime(data.getArrival().getScheduled()));
                
                int duration = 100 + (int)(Math.random() * 200);
                int price = 3500 + (int)(Math.random() * 8000);
                
                flat.put("duration", duration);
                flat.put("price", price);
                flat.put("from", data.getDeparture().getIata());
                flat.put("to", data.getArrival().getIata());
                
                flattened.add(flat);
            }
        }
        return flattened;
    }

    private String extractTime(String isoDate) {
        if (isoDate == null || isoDate.length() < 16) return "12:00";
        try {
            return isoDate.substring(11, 16);
        } catch (Exception e) {
            return "12:00";
        }
    }
}
