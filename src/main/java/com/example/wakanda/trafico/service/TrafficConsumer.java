package com.example.wakanda.trafico.service;

import com.example.wakanda.trafico.model.TrafficSensor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TrafficConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTrafficData(TrafficSensor sensor) {
        System.out.println("Mensaje recibido de Kafka: " + sensor);
    }
}