package com.learning.kafkaktableaggexample.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface WordsListenerBinding {

    @Input("words-input-channel")
    KStream<String, String> wordsInputStream();

}
