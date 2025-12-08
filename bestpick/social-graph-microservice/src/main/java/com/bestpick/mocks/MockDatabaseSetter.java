package com.bestpick.mocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.bestpick.repository.SocialGraphRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MockDatabaseSetter implements CommandLineRunner {

    @Value("${spring.profiles.active:localhost}")
    String profile;

    @Autowired
    SocialGraphRepository socialGraphRepository;

    @Override
    public void run(String... args) {

        if (profile.equals("dev")) {

            // delete DB
            socialGraphRepository.deleteAll();

            // Add mock relations

            // User 1
            socialGraphRepository.createFollowRelation(1L, 2L);
            socialGraphRepository.createFollowRelation(1L, 3L);
            socialGraphRepository.createFollowRelation(1L, 4L);

            // User 2
            socialGraphRepository.createFollowRelation(2L, 1L);
            socialGraphRepository.createFollowRelation(2L, 3L);
            socialGraphRepository.createFollowRelation(2L, 4L);

            // User 3
            socialGraphRepository.createFollowRelation(3L, 1L);
            socialGraphRepository.createFollowRelation(3L, 2L);
            socialGraphRepository.createFollowRelation(3L, 4L);

            // User 4
            socialGraphRepository.createFollowRelation(4L, 1L);
            socialGraphRepository.createFollowRelation(4L, 5L);

            // User 5
            socialGraphRepository.createFollowRelation(5L, 4L);
            socialGraphRepository.createFollowRelation(5L, 6L);

            // User 6
            socialGraphRepository.createFollowRelation(6L, 4L);
            socialGraphRepository.createFollowRelation(6L, 7L);

            // User 7
            socialGraphRepository.createBlockingRelation(7L, 8L);

            // User 8
            socialGraphRepository.createFollowRelation(8L, 9L);
            socialGraphRepository.createBlockingRelation(8L, 7L);

            // User 9
            socialGraphRepository.createFollowRelation(9L, 10L);

            // User 10
            socialGraphRepository.createBlockingRelation(10L, 5L);

            // User 11
            socialGraphRepository.createFollowRelation(11L, 12L);
            socialGraphRepository.createFollowRelation(11L, 13L);
            socialGraphRepository.createFollowRelation(11L, 14L);

            // User 12
            socialGraphRepository.createFollowRelation(12L, 11L);
            socialGraphRepository.createFollowRelation(12L, 13L);

            // User 13
            socialGraphRepository.createFollowRelation(13L, 11L);

            // User 14
            // (sin relaciones)

            // User 15
            socialGraphRepository.createBlockingRelation(15L, 11L);

            // User 16
            socialGraphRepository.createBlockingRelation(16L, 17L);

            // User 17
            socialGraphRepository.createFollowRelation(17L, 18L);

            // User 18
            socialGraphRepository.createFollowRelation(18L, 16L);

            // User 19
            socialGraphRepository.createFollowRelation(19L, 18L);
            socialGraphRepository.createBlockingRelation(19L, 20L);

            // User 20
            socialGraphRepository.createFollowRelation(20L, 18L);

            // User 21
            socialGraphRepository.createFollowRelation(21L, 22L);

            // User 22
            socialGraphRepository.createFollowRelation(22L, 23L);

            // User 23
            socialGraphRepository.createFollowRelation(23L, 24L);

            // User 24
            socialGraphRepository.createFollowRelation(24L, 21L);

            // User 25
            socialGraphRepository.createFollowRelation(25L, 26L);

            // User 26
            socialGraphRepository.createFollowRelation(26L, 25L);

            // User 27
            socialGraphRepository.createBlockingRelation(27L, 25L);

            // User 28
            socialGraphRepository.createBlockingRelation(28L, 26L);

            // User 29
            socialGraphRepository.createFollowRelation(29L, 27L);
            socialGraphRepository.createFollowRelation(29L, 28L);

            // User 30
            socialGraphRepository.createBlockingRelation(30L, 29L);
            socialGraphRepository.createBlockingRelation(30L, 24L);

            // User 31
            socialGraphRepository.createFollowRelation(31L, 32L);
            socialGraphRepository.createFollowRelation(31L, 33L);

            // User 32
            socialGraphRepository.createFollowRelation(32L, 31L);
            socialGraphRepository.createFollowRelation(32L, 33L);

            // User 33
            socialGraphRepository.createFollowRelation(33L, 31L);
            socialGraphRepository.createFollowRelation(33L, 32L);

            // User 34
            socialGraphRepository.createFollowRelation(34L, 31L);
            socialGraphRepository.createFollowRelation(34L, 32L);

            // User 35
            socialGraphRepository.createBlockingRelation(35L, 31L);

            // User 36
            socialGraphRepository.createBlockingRelation(36L, 35L);
            socialGraphRepository.createBlockingRelation(36L, 37L);

            // User 37
            socialGraphRepository.createFollowRelation(37L, 35L);

            // User 38
            socialGraphRepository.createFollowRelation(38L, 39L);

            // User 39
            socialGraphRepository.createFollowRelation(39L, 40L);

            // User 40
            socialGraphRepository.createFollowRelation(40L, 38L);

            // User 41
            socialGraphRepository.createFollowRelation(41L, 42L);
            socialGraphRepository.createFollowRelation(41L, 43L);

            // User 42
            socialGraphRepository.createFollowRelation(42L, 41L);
            socialGraphRepository.createFollowRelation(42L, 44L);

            // User 43
            socialGraphRepository.createBlockingRelation(43L, 44L);

            // User 44
            socialGraphRepository.createBlockingRelation(44L, 41L);

            // User 45
            socialGraphRepository.createBlockingRelation(45L, 43L);
            socialGraphRepository.createBlockingRelation(45L, 46L);

            // User 46
            socialGraphRepository.createBlockingRelation(46L, 45L);

            // User 47
            socialGraphRepository.createFollowRelation(47L, 41L);
            socialGraphRepository.createFollowRelation(47L, 45L);

            // User 48
            // (no relations)

            // User 49
            // (no relations)

            // User 50
            // (no relations)

        }
    };
}
