package com.proxyseller.twitter.service

import com.proxyseller.twitter.entity.Follow
import com.proxyseller.twitter.entity.User
import com.proxyseller.twitter.repository.FollowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FollowService {

    @Autowired
    private FollowRepository followRepository

    def save(Follow follow) {
        return followRepository.save(follow)
    }


    def delete(Follow follow) {
        followRepository.delete(follow)
    }

    def findByFollowerIdAndFollowingId(User user1, User user2) {
        return followRepository.findByFollowerIdAndFollowingId(user1, user2)
    }
}
