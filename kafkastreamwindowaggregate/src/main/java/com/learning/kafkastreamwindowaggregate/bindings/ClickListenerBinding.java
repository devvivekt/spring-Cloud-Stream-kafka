package com.learning.kafkastreamwindowaggregate.bindings;

import com.learning.kafkastreamwindowaggregate.model.UserClick;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface ClickListenerBinding {

    @Input("click-input-channel")
    KStream<String, UserClick> clickInputStream();
}
