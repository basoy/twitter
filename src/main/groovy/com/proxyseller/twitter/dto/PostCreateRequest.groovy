package com.proxyseller.twitter.dto


class PostCreateRequest {

    String content
    String userId

    PostCreateRequest(String content, String userId) {
        this.content = content
        this.userId = userId
    }

    PostCreateRequest() {
    }
}
