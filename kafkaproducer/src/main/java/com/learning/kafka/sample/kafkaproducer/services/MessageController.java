package com.learning.kafka.sample.kafkaproducer.services;

import com.learning.kafka.sample.kafkaproducer.model.IncomingMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class MessageController {

    @Autowired
    MessageProducer messageProducer;
    @PostMapping("/post")
    public String postMessage(@RequestBody IncomingMessage msg){
        messageProducer.sendMessage(msg.getTopic(), msg.getKey(), msg.getValue());
        return "successfully Sent message ";
    }


    @PostMapping("/test")
    public ResponseEntity<String> testMessage(@RequestBody IncomingMessage msg){
        log.info("testing Controller");
        return new ResponseEntity<String>(String.format("testing Controller %s",msg.getValue()), HttpStatus.OK);
    }


}
