package com.learning.kafkaproducerxmlbranching.services;

import com.learning.kafkaproducerxmlbranching.config.AppSerdes;
import com.learning.kafkaproducerxmlbranching.model.Order;
import com.learning.kafkaproducerxmlbranching.model.OrderEnvelop;
import jdk.jfr.Enabled;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.xml.bind.*;
import java.io.StringReader;
import java.security.cert.CertificateNotYetValidException;

@Service
@Log4j2
@EnableBinding
public class OrderListenerService {


    @Value("${application.configs.error.topic.name}")
    private String ERROR_TOPIC;
    @StreamListener("xml-input-channel")
    @SendTo({"india-orders-channel", "abroad-orders-channel"})
    public KStream<String, Order>[] process(KStream<String, String> input){
        input.foreach((k, v) -> log.info(String.format("Received XML Order with Key: %s, value %s",k, v)));

        KStream<String, OrderEnvelop> orderEnvelopKStream =
                input.map((k,v) ->
                {
                    OrderEnvelop envlop = new OrderEnvelop();
                    envlop.setXmlOrderKey(k);
                    envlop.setXmlOrderValue(v);
                    try{
                        JAXBContext context = JAXBContext.newInstance(Order.class);
                        Unmarshaller unmarshaller = context.createUnmarshaller();
                        envlop.setValidOrder((Order) unmarshaller.unmarshal(new StringReader(v)));
                        envlop.setOrderTag("VALID_ORDER");
                        if(envlop.getValidOrder().getShipTo().getCity().isEmpty()){
                            log.error("Address Missing");
                            envlop.setOrderTag("ADDRESS_MISSING_ERROR");

                        }
                    }catch(JAXBException ex){
                        log.error("failed to unmarshall the incoming xml message");
                        envlop.setOrderTag("XML_PARSING_ERROR");
                    }
                    return KeyValue.pair(envlop.getOrderTag(),envlop);
                });

        orderEnvelopKStream.filter((k,v) -> !k.equalsIgnoreCase("VALID_ORDER"))
                .to(ERROR_TOPIC, Produced.with(AppSerdes.String(), AppSerdes.OrderEnvelop()));

        KStream<String, Order> validOrders = orderEnvelopKStream
                .filter((k,v) -> k.equalsIgnoreCase("VALID_ORDER"))
                .map((k,v) -> KeyValue.pair(v.getValidOrder().getOrderId(),v.getValidOrder()));

        Predicate<String, Order> isIndiaOrder = (k,v) -> v.getShipTo().getCountry().equalsIgnoreCase("india");
        Predicate<String, Order> isAbroadOrder = (k,v) -> !v.getShipTo().getCountry().equalsIgnoreCase("india");

        return validOrders.branch(isIndiaOrder, isAbroadOrder);
    }

}
