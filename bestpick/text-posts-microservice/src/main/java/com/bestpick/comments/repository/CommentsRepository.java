package com.bestpick.comments.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestpick.comments.model.Comment;

public interface CommentsRepository extends MongoRepository<Comment, String> {

	List<Comment> findByUserIdAndTextPost_Id(Long userId, String postId);

	List<Comment> findByUserId(Long userId);

	List<Comment> findByTextPost_Id(String postId);

	@DeleteQuery("{ 'textPost.$id': { $in: ?0 } }")
	Long deleteByTextPostIdIn(List<ObjectId> postIds);

	Long deleteByUserId(Long userId);

}
