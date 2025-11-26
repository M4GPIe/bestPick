package com.bestpick.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.bestpick.kafka.events.UserEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendCreateUserEvent(UserEvent data) {

        Message<UserEvent> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "create-user")
                .build();

        log.info("Sending message {} to create-user", message);

        kafkaTemplate.send(message);
    }

    public void sendDeleteUserEvent(UserEvent data) {

        Message<UserEvent> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "delete-user")
                .build();

        log.info("Sending message {} to delete-user", message);

        kafkaTemplate.send(message);
    }
}
