package com.bestpick.textPosts.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bestpick.textPosts.model.TextPost;

@Repository
public class TextPostRepositoryImpl implements TextPostRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TextPost> findByUserIdAndHashtags(String userId, List<String> hashtags) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (userId != null && userId.length() > 0) {
            criteriaList.add(Criteria.where("userId").is(userId));
        }

        if (hashtags != null && !hashtags.isEmpty()) {
            List<Criteria> orHashtags = hashtags.stream()
                    .map(tag -> Criteria.where("hashtags." + tag).gt(0))
                    .toList();

            criteriaList.add(new Criteria().orOperator(orHashtags.toArray(Criteria[]::new)));
        }

        Query query = new Query();

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(Criteria[]::new)));
        }

        return mongoTemplate.find(query, TextPost.class);

    }
}
