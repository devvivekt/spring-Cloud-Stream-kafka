package com.learning.kafkaproducerxmlbranching.model;

import lombok.Data;
import com.learning.kafkaproducerxmlbranching.model.Order;

@Data
public class OrderEnvelop {
    String xmlOrderKey;
    String xmlOrderValue;

    String orderTag;
    Order validOrder;
}
