package com.learning.kafkastreamjoinktable.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDetails {
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("LoginID")
    private String loginID;
    @JsonProperty("LastLogin")
    private Long lastLogin;

}
