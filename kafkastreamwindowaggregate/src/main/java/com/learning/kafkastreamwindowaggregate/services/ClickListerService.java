package com.learning.kafkastreamwindowaggregate.services;

import com.learning.kafkastreamwindowaggregate.bindings.ClickListenerBinding;
import com.learning.kafkastreamwindowaggregate.model.UserClick;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Log4j2
@Service
@EnableBinding(ClickListenerBinding.class)
public class ClickListerService {

    /*
    Session window is of variable length , it depends upon how many time the user returns to application
    within the configured duration, we use session window for this
     */
    @StreamListener("click-input-channel")
    public void process(KStream<String, UserClick> input) {

        input.peek((k, v) -> log.info("Key = " + k + " Created Time = "
                        + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                .groupByKey()
                .windowedBy(SessionWindows.with(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .foreach((k, v) -> log.info(
                        "UserID: " + k.key() +
                                " Window start: " +
                                Instant.ofEpochMilli(k.window().start())
                                        .atOffset(ZoneOffset.UTC) +
                                " Window end: " +
                                Instant.ofEpochMilli(k.window().end())
                                        .atOffset(ZoneOffset.UTC) +
                                " Count: " + v +
                                " Window#: " + k.window().hashCode()
                ));

    }
}
