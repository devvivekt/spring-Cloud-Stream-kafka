package com.learning.kafkaproducer.avro.services.datagenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.kafkaproducer.avro.model.LineItem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Random;

@Service
public class ProductGenerator {

    private static final ProductGenerator ourInstance = new ProductGenerator();

    private final Random random;
    private final Random qty;
    private final LineItem[] products;

    static ProductGenerator getInstance() {
        return ourInstance;
    }
    public ProductGenerator(){
        final String DATAFILE = "src/main/resources/data/products.json";
        final ObjectMapper mapper;
        random = new Random();
        qty = new Random();
        mapper = new ObjectMapper();
        try{
            products = mapper.readValue(new File(DATAFILE), LineItem[].class);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private int getIndex(){
        return random.nextInt(100);
    }

    private int getQuantity(){
        return qty.nextInt(2)+1;
    }

    public LineItem getNextProduct(){
        LineItem item = products[getIndex()];
        item.setItemQty(getQuantity());
        item.setTotalValue(item.getItemPrice()*item.getItemQty());
        return item;
    }

}
