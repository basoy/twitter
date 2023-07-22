package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entitiy.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
}
