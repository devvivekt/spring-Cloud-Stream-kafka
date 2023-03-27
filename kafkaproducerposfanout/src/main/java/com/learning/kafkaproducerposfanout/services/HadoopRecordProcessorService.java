package com.learning.kafkaproducerposfanout.services;

import com.learning.kafkaproducerposfanout.model.HadoopRecord;
import com.learning.kafkaproducerposfanout.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
public class HadoopRecordProcessorService {

    @Autowired
    RecordBuilder builder;

    @Bean
    public Function<KStream<String, PosInvoice>, KStream<String, HadoopRecord>> hadoopsink(){

        return payload ->
        {
            KStream<String, HadoopRecord> hrkStream = payload.mapValues(v -> builder.getMaskedPosInvoice(v))
                    .flatMapValues(v -> builder.getHadoopRecord(v));

            hrkStream.foreach((k, v) ->
                    log.info(String.format("HadoopRecord :- Key: %s Value: %s",k, v)));

            return hrkStream;
        };

    }
}
