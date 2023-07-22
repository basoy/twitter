package com.proxyseller.twitter.dto

class UserUpdateRequest {
    String username
    String email
    String password

    UserUpdateRequest(String username, String email, String password) {
        this.username = username
        this.email = email
        this.password = password
    }

    UserUpdateRequest() {
    }
}
