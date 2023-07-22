package com.proxyseller.twitter.dto

class UserRegistrationRequest {
    String username
    String email
    String password
    List<String> following

    UserRegistrationRequest() {
    }

    UserRegistrationRequest(String username, String email, String password, List<String> following) {
        this.username = username
        this.email = email
        this.password = password
        this.following = following
    }
}
