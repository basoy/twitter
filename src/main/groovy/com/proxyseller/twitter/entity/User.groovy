package com.proxyseller.twitter.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {
    @Id
    String id
    String username
    String email
    String password
    @DBRef
    List<User> following = []

    User(String username, String email, String password, List<User> following) {
        this.username = username
        this.email = email
        this.password = password
        this.following = following
    }
}