package com.learning.kafka.kafkaproducer.json.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTestMessage() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topic\": \"users\", \"key\": \"41\", \"value\": \"Cabali\"}")
                ).andExpect(status().is2xxSuccessful())
                .andExpect(content().string("testing Controller Cabali"));
    }
}
