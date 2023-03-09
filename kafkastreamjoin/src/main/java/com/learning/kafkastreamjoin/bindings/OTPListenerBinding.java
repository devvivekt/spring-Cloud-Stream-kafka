package com.learning.kafkastreamjoin.bindings;

import com.learning.kafkastreamjoin.model.PaymentConfirmation;
import com.learning.kafkastreamjoin.model.PaymentRequest;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface OTPListenerBinding {

    @Input("payment-request-channel")
    KStream<String, PaymentRequest> requestInputStream();

    @Input("payment-confirmation-channel")
    KStream<String, PaymentConfirmation> confirmationInputStream();

}
