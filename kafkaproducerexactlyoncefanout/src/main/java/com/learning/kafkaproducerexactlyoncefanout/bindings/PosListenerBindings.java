package com.learning.kafkaproducerexactlyoncefanout.bindings;

import com.learning.kafkaproduceravro.model.PosInvoice;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface PosListenerBindings {
    @Input("pos-input-channel")
    KStream<String, PosInvoice> posInputStream();
}
