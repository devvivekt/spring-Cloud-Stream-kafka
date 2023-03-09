package com.learning.kafkastreamjoinktabletopthree.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdInventories {

    @JsonProperty("InventoryID")
    private String inventoryID;
    @JsonProperty("NewsType")
    private String newsType;

}
