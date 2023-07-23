package com.proxyseller.twitter.controller

import com.proxyseller.twitter.dto.UserRegistrationRequest
import com.proxyseller.twitter.dto.UserUpdateRequest
import com.proxyseller.twitter.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService userService

    @PostMapping("/register")
    def registerUser(@RequestBody UserRegistrationRequest request) {
        String username = request.getUsername()
        String email = request.getEmail()
        String password = request.getPassword()
        List<String> following = request.getFollowing()
        return userService.createUser(username, email, password, following)
    }

    @PutMapping("/{userId}")
    def updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        String username = request.getUsername()
        String email = request.getEmail()
        String password = request.getPassword()
        return userService.updateUser(userId, username, email, password)
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId)
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    def followUser(@PathVariable String followerId, @PathVariable String followingId) {
        userService.followUser(followerId, followingId)
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    def unfollowUser(@PathVariable String followerId, @PathVariable String followingId) {
        userService.unfollowUser(followerId, followingId)
    }

    @GetMapping("/{userId}/feed")
    def getUserFeed(@PathVariable String userId) {
       return userService.getUserFeed(userId)
    }
}
