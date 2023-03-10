package com.learning.kafkastreamjoinktable.bindings;

import com.learning.kafkastreamjoinktable.model.UserDetails;
import com.learning.kafkastreamjoinktable.model.UserLogin;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;

public interface UserListenerBinding {

    @Input("user-master-channel")
    KTable<String, UserDetails> userInputStream();

    @Input("user-login-channel")
    KTable<String, UserLogin> loginInputStream();
}
