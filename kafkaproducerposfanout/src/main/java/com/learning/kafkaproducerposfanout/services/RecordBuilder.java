package com.learning.kafkaproducerposfanout.services;

import com.learning.kafkaproducerposfanout.model.HadoopRecord;
import com.learning.kafkaproducerposfanout.model.LineItem;
import com.learning.kafkaproducerposfanout.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.learning.kafkaproducerposfanout.model.Notification;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class RecordBuilder {

    public Notification getNotification(PosInvoice posInvoice){
        Notification notification = new Notification();
        notification.setInvoiceNumber(posInvoice.getInvoiceNumber());
        notification.setCustomerCardNo(posInvoice.getInvoiceNumber());
        notification.setTotalAmount(posInvoice.getTotalAmount());
        notification.setEarnedLoyaltyPoints(posInvoice.getTotalAmount()*0.02);
        return notification;
    }

    public PosInvoice getMaskedPosInvoice(PosInvoice posInvoice){
        posInvoice.setCustomerCardNo(null);
        if(posInvoice.getDeliveryType().equalsIgnoreCase("HOME-DELIVERY")){
            posInvoice.getDeliveryAddress().setAddressLine(null);
            posInvoice.getDeliveryAddress().setContactNumber(null);
        }
        return posInvoice;
    }

    public List<HadoopRecord> getHadoopRecord(PosInvoice posInvoice){
        List<HadoopRecord> hadoopRecords = new ArrayList<>();
        for(LineItem li : posInvoice.getInvoiceLineItems()){
            HadoopRecord hr = new HadoopRecord();
            hr.setInvoiceNumber(posInvoice.getInvoiceNumber());
            hr.setCreatedTime(posInvoice.getCreatedTime());
            hr.setStoreID(posInvoice.getStoreID());
            hr.setPosID(posInvoice.getPosID());
            hr.setCustomerType(posInvoice.getCustomerType());
            hr.setPaymentMethod(posInvoice.getPaymentMethod());
            hr.setDeliveryType(posInvoice.getDeliveryType());
            if(posInvoice.getDeliveryType().equalsIgnoreCase("HOME-DELIVERY")) {
                hr.setPinCode(posInvoice.getDeliveryAddress().getPinCode());
                hr.setState(posInvoice.getDeliveryAddress().getState());
                hr.setCity(posInvoice.getDeliveryAddress().getCity());
            }
            hr.setItemCode(li.getItemCode());
            hr.setItemDescription(li.getItemDescription());
            hr.setItemPrice(li.getItemPrice());
            hr.setItemQty(li.getItemQty());
            hr.setTotalValue(li.getTotalValue());
            hadoopRecords.add(hr);
        }
        return hadoopRecords;
    }

}
