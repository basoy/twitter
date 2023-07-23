package com.proxyseller.twitter.entity

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
    @DBRef
    List<Comment> comments = []
    @DBRef
    List<Like> likes = []

    Post(String content, User author) {
        this.content = content
        this.author = author
        this.createdAt = new Date()
    }

    void addComment(Comment comment) {
        if (comments == null) {
            comments = []
        }
        comments.add(comment)
    }

    void addLike(Like like) {
        if (likes == null) {
            likes = []
        }
        likes.add(like)
    }

    void removeLike(Like like) {
        if (likes != null) {
            likes.remove(like)
        }
    }
}