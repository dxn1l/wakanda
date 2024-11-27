package com.example.wakanda.trafico.controller;

import com.example.wakanda.trafico.model.TrafficSensor;
import com.example.wakanda.trafico.service.TrafficService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    private final TrafficService trafficService;

    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @GetMapping("/{location}")
    public ResponseEntity<List<TrafficSensor>> getTrafficData(@PathVariable String location) {
        return ResponseEntity.ok(trafficService.getTrafficData(location));
    }

    @PostMapping
    public ResponseEntity<TrafficSensor> addTrafficData(@RequestBody TrafficSensor sensor) {
        return ResponseEntity.status(201).body(trafficService.saveTrafficData(sensor));
    }
}