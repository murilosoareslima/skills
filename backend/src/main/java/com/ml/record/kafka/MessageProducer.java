package com.ml.record.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

@Log
@Component
public class MessageProducer {
    
    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void sendMessage(T object) {
        log.info("Enviando mensagem para o tópico do kafka -> " + String.format("Mensagem: ", object));
        kafkaTemplate.send(topic, object);
    }

}
