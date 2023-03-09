package com.learning.kafkastreamjoinktabletopthree.bindings;

import com.learning.kafkastreamjoinktabletopthree.model.AdClick;
import com.learning.kafkastreamjoinktabletopthree.model.AdInventories;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface ClicksListenerBinding {
    @Input("inventories-channel")
    GlobalKTable<String, AdInventories> inventoryInputStream();

    @Input("clicks-channel")
    KStream<String, AdClick> clickInputStream();
}
