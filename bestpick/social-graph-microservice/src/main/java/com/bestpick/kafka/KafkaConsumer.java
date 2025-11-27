package com.bestpick.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bestpick.kafka.events.UserEvent;
import com.bestpick.model.User;
import com.bestpick.service.SocialGraphService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    SocialGraphService socialGraphService;

    @KafkaListener(topics = "create-user", groupId = "social-graph-create")
    public void consumeCreatedUser(UserEvent msg) {
        socialGraphService.createUserNode(new User(msg.getId()));
    }

    @KafkaListener(topics = "delete-user", groupId = "social-graph-delete")
    public void consumeDeletedUser(UserEvent msg) {
        socialGraphService.deleteUserNode(new User(msg.getId()));
    }
}
