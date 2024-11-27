package com.example.wakanda.trafico.service;

import com.example.wakanda.trafico.model.TrafficSensor;
import com.example.wakanda.trafico.repository.TrafficRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final KafkaTemplate<String, TrafficSensor> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public TrafficService(TrafficRepository trafficRepository, KafkaTemplate<String, TrafficSensor> kafkaTemplate) {
        this.trafficRepository = trafficRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<TrafficSensor> getTrafficData(String location) {
        return trafficRepository.findByLocation(location);
    }

    public TrafficSensor saveTrafficData(TrafficSensor sensor) {
        sensor.setTimestamp(LocalDateTime.now());
        TrafficSensor savedSensor = trafficRepository.save(sensor);

        // Publicar mensaje en Kafka
        kafkaTemplate.send(topicName, savedSensor);

        return savedSensor;
    }
}