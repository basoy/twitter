package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entity.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String>{

    List<Comment> findByPostId(String s)
}