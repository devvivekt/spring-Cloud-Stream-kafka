package com.learning.kafkaproduceravroposfanout.bindings;

import com.learning.kafkaproduceravro.model.PosInvoice;
import com.learning.kafkaproduceravroposfanout.model.Notification;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface PosListenerBindings {
    @Input("notification-input-channel")
    KStream<String, PosInvoice> notificationInputStream();
    @Output("notification-output-channel")
    KStream<String, Notification> notificationOutputStream();
    @Input("hadoop-input-channel")
    KStream<String, PosInvoice> hadoopInputStream();
    @Output("hadoop-output-channel")
    KStream<String, Notification> hadoopOutputStream();

}
