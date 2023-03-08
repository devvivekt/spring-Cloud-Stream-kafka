package com.learning.kafkaproduceravroposfanout.services;


import com.learning.kafkaproduceravro.model.PosInvoice;
import com.learning.kafkaproduceravroposfanout.bindings.PosListenerBindings;
import com.learning.kafkaproduceravroposfanout.model.HadoopRecord;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableBinding(PosListenerBindings.class)
public class HadoopRecordProcessorService {

    @Autowired
    RecordBuilder builder;

    @StreamListener("hadoop-input-channel")
    @SendTo("hadoop-output-channel")
    public KStream<String, HadoopRecord> process(KStream<String, PosInvoice> input){
        KStream<String, HadoopRecord> hrkStream = input.mapValues(v -> builder.getMaskedPosInvoice(v))
                .flatMapValues(v -> builder.getHadoopRecord(v));

        hrkStream.foreach((k, v) ->
        log.info(String.format("HadoopRecord :- Key: %s Value: %s",k, v)));

        return hrkStream;
    }
}
