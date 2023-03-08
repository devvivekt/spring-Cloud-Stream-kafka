package com.learning.kafkakrewards.services;

import com.learning.kafkaproduceravro.model.Notification;
import com.learning.kafkaproduceravro.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RecordBuilder {

    public Notification getNotification(PosInvoice posInvoice){
        Notification notification = new Notification();
        notification.setInvoiceNumber(posInvoice.getInvoiceNumber());
        notification.setCustomerCardNo(posInvoice.getInvoiceNumber());
        notification.setTotalAmount(posInvoice.getTotalAmount());
        notification.setEarnedLoyaltyPoints(posInvoice.getTotalAmount()*0.02);
        notification.setTotalLoyaltyPoints(notification.getEarnedLoyaltyPoints());
        return notification;
    }

}
