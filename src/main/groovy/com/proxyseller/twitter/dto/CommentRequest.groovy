package com.proxyseller.twitter.dto

class CommentRequest {
    String userId
    String content

    CommentRequest(String userId, String content) {
        this.userId = userId
        this.content = content
    }

    CommentRequest() {
    }
}