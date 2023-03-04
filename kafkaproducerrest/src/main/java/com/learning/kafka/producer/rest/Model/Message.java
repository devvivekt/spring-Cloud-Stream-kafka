package com.learning.kafka.producer.rest.Model;


import lombok.Data;

@Data
public class Message {

    private String topic;
    private String key;
    private String value;
}
