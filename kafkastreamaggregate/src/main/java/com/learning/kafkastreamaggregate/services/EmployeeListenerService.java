package com.learning.kafkastreamaggregate.services;

import com.learning.kafkaproduceravro.model.Employee;
import com.learning.kafkastreamaggregate.bindings.EmployeeListenerBinding;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableBinding(EmployeeListenerBinding.class)
public class EmployeeListenerService {

    @Autowired
    RecordBuilder builder;


    //for people transfer department , need to use ktable
    @StreamListener("employee-input-channel")
    public void process(KStream<String, Employee> input){
        input.peek((k,v) -> log.info("Key: {} , value: {}",k,v))
                .groupBy((k,v) -> v.getDepartment())
                .aggregate(
                        () -> builder.init(),
                        (k,v, aggV) -> builder.aggregate(v, aggV)
                ).toStream()
                .foreach((k,v) -> log.info("Department Key: {} , Department value: {}",k,v.toString()));
    }

    @StreamListener("employee-input-channel")
    public void processUsingKtalbe(KStream<String, Employee> input){
        input.map((k, v) -> KeyValue.pair(v.getId(),v))
                .peek((k,v) -> log.info("Key: {} , value: {}",k,v))
                .toTable()
                .groupBy((k,v) -> KeyValue.pair(v.getDepartment(),v))
                .aggregate(
                        () -> builder.init(),
                        (k,v, aggV) -> builder.aggregate(v, aggV),
                        (k,v, aggV) -> builder.substract(v,aggV)
                ).toStream()
                .foreach((k,v) -> log.info("Department Key: {} , Department value: {}",k,v.toString()));
    }


}
