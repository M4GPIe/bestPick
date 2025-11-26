package com.bestpick.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bestpick.kafka.events.UserEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "create-user", groupId = "myGroup")
    public void consumeCreatedUser(UserEvent msg) {
        log.info("Consuming the message from create-user:: {}", msg);
    }

    @KafkaListener(topics = "delete-user", groupId = "myGroup")
    public void consumeDeletedUser(UserEvent msg) {
        log.info("Consuming the message from delete-user:: {}", msg);
    }
}
