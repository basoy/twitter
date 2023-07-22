package com.proxyseller.twitter.entitiy

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

}
