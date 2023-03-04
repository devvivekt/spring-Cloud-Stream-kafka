package com.learning.kafkaproduceravro.services.datagenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.learning.kafkaproduceravro.model.DeliveryAddress;
import java.io.File;
import java.util.Random;

@Service
public class AddressGenerator {

    private static final AddressGenerator ourInstance = new AddressGenerator();

    private final Random random;
    private final DeliveryAddress[] addresses;

    static AddressGenerator getInstance() {
        return ourInstance;
    }
    public AddressGenerator(){
        final String DATAFILE = "src/main/resources/data/address.json";
        final ObjectMapper mapper;
        random = new Random();
        mapper = new ObjectMapper();
        try{
            addresses = mapper.readValue(new File(DATAFILE), DeliveryAddress[].class);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private int getIndex(){
        return random.nextInt(100);
    }

    public DeliveryAddress nextAddress(){
        return addresses[getIndex()];
    }

}
