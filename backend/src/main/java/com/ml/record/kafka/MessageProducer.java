package com.ml.record.kafka;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.java.Log;

@Log
@Component
public class MessageProducer {
    
    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public <T> boolean sendMessage(T object) {
        log.info("Enviando mensagem para o tópico do kafka -> " + String.format("Mensagem: ", object));        
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topic, object);
        try {
            SendResult<String, Object> sendResult = send.get();
            return sendResult.getProducerRecord().value() != null;
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
