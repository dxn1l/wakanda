package com.example.wakanda.trafico.config;

import com.example.wakanda.trafico.model.TrafficSensor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Crear el topic de Kafka
    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topicName, 1, (short) 1);
    }

    // Configurar KafkaTemplate para usar JsonSerializer
    @Bean
    public KafkaTemplate<String, TrafficSensor> kafkaTemplate() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Serializador JSON para los valores
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class); // Serializador String para la clave

        ProducerFactory<String, TrafficSensor> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        return new KafkaTemplate<>(producerFactory);
    }

    // Configuración del deserializador para los valores de TrafficSensor
    @Bean
    public JsonDeserializer<TrafficSensor> trafficSensorDeserializer() {
        JsonDeserializer<TrafficSensor> deserializer = new JsonDeserializer<>(TrafficSensor.class);
        deserializer.addTrustedPackages("com.example.wakanda.trafico.model");  // Añadir la clase como paquete confiable
        deserializer.setUseTypeMapperForKey(true);  // Usar el tipo correcto para las claves
        return deserializer;
    }
}
