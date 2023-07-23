package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    def findByUsername(String username)
}
