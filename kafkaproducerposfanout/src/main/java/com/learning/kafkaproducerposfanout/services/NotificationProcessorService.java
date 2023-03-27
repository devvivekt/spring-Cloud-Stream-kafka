package com.learning.kafkaproducerposfanout.services;

import com.learning.kafkaproducerposfanout.model.Notification;
import com.learning.kafkaproducerposfanout.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
public class NotificationProcessorService {

    @Autowired
    RecordBuilder builder;

    @Bean
    public Function<KStream<String, PosInvoice>, KStream<String, Notification>> notification(){

        return payload ->
        {
            KStream<String, Notification> notificationKStream =
                    payload.filter((k ,v ) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                            .mapValues( v -> builder.getNotification(v));

            notificationKStream.foreach((k, v)
                    -> log.info(String.format("Notification :- Key: %s Value: %s",k, v)));

            return notificationKStream;
        };

    }
}
