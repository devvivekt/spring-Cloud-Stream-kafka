package com.learning.kafkaproduceravroposfanout.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {
    @JsonProperty("InvoiceNumber")
    private String invoiceNumber;
    @JsonProperty("CustomerCardNo")
    private String customerCardNo;
    @JsonProperty("TotalAmount")
    private Double totalAmount;
    @JsonProperty("EarnedLoyaltyPoints")
    private Double earnedLoyaltyPoints;
}
