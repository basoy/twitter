package com.proxyseller.twitter.entitiy

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
class Post {
    @Id
    String id
    String content
    Date createdAt
    @DBRef
    User author

    Post(String content, User author) {
        this.content = content
        this.author = author
        this.createdAt = new Date()
    }
}