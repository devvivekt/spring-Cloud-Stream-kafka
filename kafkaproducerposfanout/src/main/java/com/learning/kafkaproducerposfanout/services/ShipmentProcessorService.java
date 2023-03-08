package com.learning.kafkaproducerposfanout.services;

import com.learning.kafkaproducerposfanout.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.intellij.lang.annotations.JdkConstants;
import org.junit.jupiter.api.Timeout;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.messaging.handler.annotation.SendTo;

@Log4j2
@EnableBinding
public class ShipmentProcessorService {

    @StreamListener("shipment-input-channel")
    @SendTo("shipment-output-channel")
    KStream<String, PosInvoice> process(KStream<String, PosInvoice> input){

        KStream<String, PosInvoice> output = input.filter((k,v) -> v.getDeliveryType().equalsIgnoreCase("HOME-DELIVERY"));
        output.foreach((k,v) -> log.info(String.format("Shipment :- Key: %s Value: %s",k, v)));
        return output;
    }
}
