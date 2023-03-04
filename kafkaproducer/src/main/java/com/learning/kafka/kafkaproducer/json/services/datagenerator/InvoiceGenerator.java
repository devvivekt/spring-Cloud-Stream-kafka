package com.learning.kafka.kafkaproducer.json.services.datagenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.kafka.kafkaproducer.json.model.LineItem;
import com.learning.kafka.kafkaproducer.json.model.PosInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InvoiceGenerator {

    private static InvoiceGenerator ourInstance = new InvoiceGenerator();

    private final Random random;
    private final Random invoiceNumber;
    private final Random numberOfItems;
    private final PosInvoice[] invoices;


    @Autowired
    AddressGenerator addressGenerator;

    @Autowired
    ProductGenerator productGenerator;

    public static InvoiceGenerator getInstance() {
        return ourInstance;
    }
    public InvoiceGenerator(){
        final String DATAFILE = "src/main/resources/data/Invoice.json";
        final ObjectMapper mapper;
        random = new Random();
        numberOfItems = new Random();
        invoiceNumber = new Random();
        mapper = new ObjectMapper();
        try{
            invoices = mapper.readValue(new File(DATAFILE), PosInvoice[].class);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private int getNewInvoiceNumber() {
        return invoiceNumber.nextInt(99999999) + 99999;
    }


    private int getIndex(){
        return random.nextInt(100);
    }

    private int getNumberOfItems(){
        return numberOfItems.nextInt(4)+1;
    }

    public PosInvoice getNextInvoice(){
        PosInvoice invoice = invoices[getIndex()];
        invoice.setInvoiceNumber(Integer.toString(getNewInvoiceNumber()));
        invoice.setCreatedTime(System.currentTimeMillis());
        if(invoice.getDeliveryType().equals("HOME-DELIVERY")) {
            invoice.setDeliveryAddress(addressGenerator.nextAddress());
        }
        int itemCount = getNumberOfItems();
        Double totalAmount = 0.0;
        List<LineItem> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            LineItem item = productGenerator.getNextProduct();
            totalAmount = totalAmount + item.getTotalValue();
            items.add(item);
        }
        invoice.setNumberOfItems(itemCount);
        invoice.setInvoiceLineItems(items);
        invoice.setTotalAmount(totalAmount);
        invoice.setTaxableAmount(totalAmount);
        invoice.setCGST(totalAmount * 0.025);
        invoice.setSGST(totalAmount * 0.025);
        invoice.setCESS(totalAmount * 0.00125);

        return invoice;
    }
}
