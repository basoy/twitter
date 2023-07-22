package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entitiy.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    def findByUsername(String username)
}
