package com.learning.kafka.sample.kafkaproducer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineItem {

    @JsonProperty("ItemCode")
    private String itemCode;
    @JsonProperty("ItemDescription")
    private String itemDecription;

    @JsonProperty("ItemPrice")
    private Double itemPrice;

    @JsonProperty("ItemQty")
    private Integer itemQuantity;

    @JsonProperty("TotalValue")
    private Double totalValue;

}
