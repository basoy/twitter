package com.proxyseller.twitter.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "likes")
class Like {
    @Id
    String id
    @DBRef
    Post post
    @DBRef
    User user

    Like(Post post, User user) {
        this.post = post
        this.user = user
    }

    @JsonIgnore
    Post getPost() {
        return post
    }
}
