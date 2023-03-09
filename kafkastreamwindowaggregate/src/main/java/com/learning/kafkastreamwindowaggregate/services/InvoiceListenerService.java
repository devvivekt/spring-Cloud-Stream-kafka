package com.learning.kafkastreamwindowaggregate.services;

import com.learning.kafkaproduceravro.model.Employee;
import com.learning.kafkastreamwindowaggregate.bindings.InvoiceListenerBinding;
import com.learning.kafkastreamwindowaggregate.model.SimpleInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Service
@Log4j2
@EnableBinding(InvoiceListenerBinding.class)
public class InvoiceListenerService {



    //for using timewindow of fixed duration 5 misn
    @StreamListener("invoice-input-channel")
    public void process(KStream<String, SimpleInvoice> input){
        input.peek((k,v) -> log.info("Key: {} , Created Time: {}",k
                , Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .foreach((k,v) -> log.info("StoreId: {} , Window Start: {}, Window End: {} , count: {} , hashcode: {}"
                        , k.key()
                        , Instant.ofEpochMilli(k.window().start()).atOffset(ZoneOffset.UTC)
                        , Instant.ofEpochMilli(k.window().end()).atOffset(ZoneOffset.UTC)
                        , v
                        , k.window().hashCode()
                ));
                    }



}
