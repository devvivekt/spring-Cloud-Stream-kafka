package com.learning.kafkakrewards.bindings;

import com.learning.kafkaproduceravro.model.Notification;
import com.learning.kafkaproduceravro.model.PosInvoice;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface RewardsListenerBinding {

    @Input("invoice-input-channel")
    KStream<String, PosInvoice> inVoiceInputStream();

    @Output("notification-output-channel")
    KStream<String, Notification> notificationOutputStream();

}
