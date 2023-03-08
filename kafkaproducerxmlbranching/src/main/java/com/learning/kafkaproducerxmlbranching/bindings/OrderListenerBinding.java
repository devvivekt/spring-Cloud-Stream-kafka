package com.learning.kafkaproducerxmlbranching.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface OrderListenerBinding {

    @Input("xml-input-channel")
    KStream<String, String> inputXmlStream();

    @Output("india-orders-channel")
    KStream<String, String> indiaOutputStream();

    @Output("abroad-orders-channel")
    KStream<String, String> abroadOutPutStream();

}
