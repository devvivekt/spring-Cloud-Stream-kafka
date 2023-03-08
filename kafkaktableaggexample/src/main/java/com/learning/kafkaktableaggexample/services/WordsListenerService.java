package com.learning.kafkaktableaggexample.services;

import com.learning.kafkaktableaggexample.bindings.WordsListenerBinding;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Log4j2
@EnableBinding(WordsListenerBinding.class)
public class WordsListenerService {


    @StreamListener("words-input-channel")
    public void process(KStream<String, String> input){

        KStream<String, String> wordStream =
                input.flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")));

        wordStream.groupBy((key, value) -> value)
                .count()
                .toStream()
                .peek((k,v) -> log.info("word: {}, count: {}", k, v ));

    }

}
