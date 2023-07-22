package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entitiy.Follow
import org.springframework.data.mongodb.repository.MongoRepository;

interface FollowRepository extends MongoRepository<Follow, String>{
}
