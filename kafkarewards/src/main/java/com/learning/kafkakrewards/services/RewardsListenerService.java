package com.learning.kafkakrewards.services;

import com.learning.kafkakrewards.bindings.RewardsListenerBinding;
import com.learning.kafkaproduceravro.model.Notification;
import com.learning.kafkaproduceravro.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableBinding(RewardsListenerBinding.class)
public class RewardsListenerService {

    @Autowired
    RecordBuilder builder;

    @StreamListener("invoice-input-channel")
    @SendTo("notification-output-channel")
    public KStream<String, Notification> process(KStream<String, PosInvoice> input){
        KStream<String, Notification> notificationKStream =
                input
                        .filter((k,v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                        .map((k,v) -> new KeyValue<>(v.getCustomerCardNo(), builder.getNotification(v)))
                        .groupByKey()
                        .reduce((aggVal, newVal) ->
                                {
                                    newVal.setTotalLoyaltyPoints(newVal.getEarnedLoyaltyPoints()+aggVal.getTotalLoyaltyPoints());
                                    return newVal;
                                }
                                ).toStream();
        notificationKStream.foreach((k,v) -> log.info(String.format("Key: %s value: %s", k, v)));
        return notificationKStream;
    }

}
