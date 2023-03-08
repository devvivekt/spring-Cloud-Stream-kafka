package com.learning.kafkaproducerexactlyoncefanout.services;


import com.learning.kafkaproduceravro.model.HadoopRecord;
import com.learning.kafkaproduceravro.model.Notification;
import com.learning.kafkaproduceravro.model.PosInvoice;
import com.learning.kafkaproducerexactlyoncefanout.bindings.PosListenerBindings;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableBinding(PosListenerBindings.class)
public class PosListenerService {

    @Autowired
    RecordBuilder builder;

    @StreamListener("pos-input-channel")
    public void process(KStream<String, PosInvoice> input){
        KStream<String, HadoopRecord> hrkStream = input
                .mapValues(v -> builder.getMaskedPosInvoice(v))
                .flatMapValues(v -> builder.getHadoopRecord(v));

        KStream<String, Notification> notificationKStream = input
                .filter((k,v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                        .mapValues(v -> builder.getNotification(v));


        hrkStream.foreach((k, v) ->
        log.info(String.format("HadoopRecord :- Key: %s Value: %s",k, v)));
        notificationKStream.foreach((k, v) ->
                log.info(String.format("Notification :- Key: %s Value: %s",k, v)));

        hrkStream.to("hadoop-sink-topic");
        hrkStream.to("loyalty-topic");

    }
}
