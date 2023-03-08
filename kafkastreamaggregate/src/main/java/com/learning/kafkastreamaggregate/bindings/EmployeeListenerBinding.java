package com.learning.kafkastreamaggregate.bindings;

import com.learning.kafkaproduceravro.model.Employee;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface EmployeeListenerBinding {

    @Input("employee-input-channel")
    KStream<String, Employee> employeeInputStream();

}
