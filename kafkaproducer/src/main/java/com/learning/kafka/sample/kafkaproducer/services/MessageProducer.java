package com.learning.kafka.sample.kafkaproducer.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MessageProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage(String topic, String key, String value){
        log.info("Sending message to topic {} with key {} as and payload as {}", topic,  key
        , value);
        kafkaTemplate.send(topic, key, value);
    }


}
