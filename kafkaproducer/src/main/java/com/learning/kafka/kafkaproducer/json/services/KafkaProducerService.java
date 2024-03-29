package com.learning.kafka.kafkaproducer.json.services;

import com.learning.kafka.kafkaproducer.json.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {

    @Value("${application.configs.topic.name}")
    private String TOPIC_NAME;

    @Autowired
    KafkaTemplate<String, PosInvoice> template;

    public void sendMessage(PosInvoice posInvoice){
        log.info(String.format("Producing Invoice Number %s",posInvoice.getInvoiceNumber()));
        template.send(TOPIC_NAME,posInvoice.getStoreID(),posInvoice);
    }
}
