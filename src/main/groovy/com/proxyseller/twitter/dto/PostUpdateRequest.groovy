package com.proxyseller.twitter.dto

class PostUpdateRequest {

    String content
    String userId

    PostUpdateRequest(String content, String userId) {
        this.content = content
        this.userId = userId
    }

    PostUpdateRequest() {
    }
}
