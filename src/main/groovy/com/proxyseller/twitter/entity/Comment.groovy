package com.proxyseller.twitter.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "comments")
class Comment {
    @Id
    String id
    String content
    Date createdAt
    @DBRef
    User author
    @DBRef
    Post post

    Comment(Post post, User author, String content) {
        this.content = content
        this.author = author
        this.createdAt = new Date()
        this.post = post
    }

    @JsonIgnore
    Post getPost() {
        return post
    }
}
