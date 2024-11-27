package com.example.wakanda.trafico.repository;

import com.example.wakanda.trafico.model.TrafficSensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficRepository extends JpaRepository<TrafficSensor, Long> {
    List<TrafficSensor> findByLocation(String location);
}
