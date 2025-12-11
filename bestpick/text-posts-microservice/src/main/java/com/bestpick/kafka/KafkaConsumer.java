package com.bestpick.kafka;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestpick.comments.repository.CommentsRepository;
import com.bestpick.kafka.events.UserEvent;
import com.bestpick.textPosts.repository.TextPostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    TextPostRepository textPostRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @KafkaListener(topics = "delete-user", groupId = "text-posts-group")
    @Transactional
    public void consumeDeleteUserEvent(UserEvent event) {

        Long userId = event.getId();

        // delete user posts and their comments
        List<ObjectId> userPosts = textPostRepository.findByUserId(userId)
                .stream().map(post -> post.getId()).map(ObjectId::new).toList();

        log.info("{}", userPosts);

        Long commentsDeletedFromPosts = commentsRepository.deleteByTextPostIdIn(userPosts);

        Long postsDeleted = textPostRepository.deleteByUserId(userId);

        // delete user comments
        Long userCommentsDeleted = commentsRepository.deleteByUserId(userId);

        log.info(
                "----------------------------------\nUser had: {} posts and {} comments\nUser posts had {} comments\n------------------------------------------",
                postsDeleted, userCommentsDeleted, commentsDeletedFromPosts);

    }

}
