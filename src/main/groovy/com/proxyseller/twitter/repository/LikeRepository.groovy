package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entity.Like
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository extends MongoRepository<Like, String> {

    Like findByPostIdAndUserId(String postId, String userId)
}
