package com.travel.travelmanagement.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.travelmanagement.entity.TourPackage;
import com.travel.travelmanagement.repository.TourRepository;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @PostConstruct
    public void init() {
        if (tourRepository.count() == 0) {
            TourPackage t1 = new TourPackage();
            t1.setTitle("City Lights Wonders");
            t1.setLocation("Tokyo, Japan");
            t1.setPrice(120000.0);
            t1.setDuration(7);
            t1.setImageUrl("https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&q=80&w=800");
            
            TourPackage t2 = new TourPackage();
            t2.setTitle("Alpine Dreams");
            t2.setLocation("Swiss Alps");
            t2.setPrice(185000.0);
            t2.setDuration(5);
            t2.setImageUrl("https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?auto=format&fit=crop&q=80&w=800");
            
            TourPackage t3 = new TourPackage();
            t3.setTitle("Tropical Paradise");
            t3.setLocation("Maldives");
            t3.setPrice(150000.0);
            t3.setDuration(4);
            t3.setImageUrl("https://images.unsplash.com/photo-1514282401047-d79a71a590e8?auto=format&fit=crop&q=80&w=800");

            tourRepository.saveAll(List.of(t1, t2, t3));
        }
    }

    public TourPackage addTour(TourPackage tour) {
        return tourRepository.save(tour);
    }

    public List<TourPackage> getTours() {
        return tourRepository.findAll();
    }
}