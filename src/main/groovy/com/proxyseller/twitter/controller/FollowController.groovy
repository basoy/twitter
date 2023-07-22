package com.proxyseller.twitter.controller

import com.proxyseller.twitter.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/followers")
class FollowController {

    @Autowired
    private UserService userService

    @PostMapping("/{followerId}/follow/{followingId}")
    def followUser(@PathVariable String followerId, @PathVariable String followingId) {
        userService.followUser(followerId, followingId)
    }

    // Unfollow a user
    @PostMapping("/{followerId}/unfollow/{followingId}")
    def unfollowUser(@PathVariable String followerId, @PathVariable String followingId) {
        userService.unfollowUser(followerId, followingId)
    }
}
