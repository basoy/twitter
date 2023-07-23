package com.proxyseller.twitter.repository

import com.proxyseller.twitter.entity.Follow
import com.proxyseller.twitter.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface FollowRepository extends MongoRepository<Follow, String>{

    Follow findByFollowerIdAndFollowingId(User followerId, User followingId)
}
