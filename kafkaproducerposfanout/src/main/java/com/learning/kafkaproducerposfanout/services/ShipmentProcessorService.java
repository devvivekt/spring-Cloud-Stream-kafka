package com.learning.kafkaproducerposfanout.services;

import com.learning.kafkaproducerposfanout.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@Log4j2
public class ShipmentProcessorService {

    @Bean
    public Function<KStream<String, PosInvoice>, KStream<String, PosInvoice>> shipment(){

        return payload ->
        {
            KStream<String, PosInvoice> output =  payload.filter((k,v) -> v.getDeliveryType().equalsIgnoreCase("HOME-DELIVERY"));
            output.foreach((k,v) -> log.info(String.format("Shipment :- Key: %s Value: %s",k, v)));
            return output;
        };

    }
}
