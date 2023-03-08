package com.learning.kafkaproducerposfanout.services;

import com.learning.kafkaproducerposfanout.bindings.PosListenerBindings;
import com.learning.kafkaproducerposfanout.model.Notification;
import com.learning.kafkaproducerposfanout.model.PosInvoice;
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
public class NotificationProcessorService {

    @Autowired
    RecordBuilder builder;

    @StreamListener("notification-input-channel")
    @SendTo("notification-output-channel")
    public KStream<String, Notification> process(KStream<String, PosInvoice> input){
        KStream<String, Notification> notificationKStream =
                input.filter((k ,v ) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                        .mapValues( v -> builder.getNotification(v));

        notificationKStream.foreach((k, v)
                        -> log.info("Notification:- Key: %s, Value: %s",k ,v));

        return notificationKStream;
    }
}
