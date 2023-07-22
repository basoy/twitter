package com.proxyseller.twitter.entitiy

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "follows")
class Follow {

    @Id
    String id
    @DBRef
    User followerId
    @DBRef
    User followingId

    Follow(User followerId, User followingId) {
        this.followerId = followerId
        this.followingId = followingId
    }
}
