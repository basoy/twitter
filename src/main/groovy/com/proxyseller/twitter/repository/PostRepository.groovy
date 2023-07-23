package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entity.Post
import com.proxyseller.twitter.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
    def findByAuthor(Optional<User> user)

    def findByIdAndAuthorId(String postId, String authorId)
}
