package com.proxyseller.twitter.service

import com.proxyseller.twitter.entity.Follow
import com.proxyseller.twitter.entity.User
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private UserRepository userRepository

    @Autowired
    private PostService postService

    @Autowired
    private FollowService followService

    UserService(UserRepository userRepository, FollowService followService, PostService postService) {
        this.userRepository = userRepository
        this.followService = followService
        this.postService = postService
    }

    def createUser(String username, String email, String password, List<String> following) {
        User newUser = new User(username, email, password, following as List<User>)
        return userRepository.save(newUser)
    }

    def getUserById(String userId) {
        return userRepository.findById(userId).orElse(null)
    }

    def updateUser(String userId, String username, String email, String password) {
        User userToUpdate = getUserById(userId)
        if (userToUpdate) {
            userToUpdate.username = username
            userToUpdate.email = email
            userToUpdate.password = password
            return userRepository.save(userToUpdate)
        }
        return null
    }

    void deleteUser(String userId) {
        userRepository.deleteById(userId)
    }

    def followUser(String followerId, String followingId) {
        User follower = userRepository.findById(followerId).orElseThrow { new Exception("Follower not found.") }
        User following = userRepository.findById(followingId).orElseThrow { new Exception("User to follow not found.") }

        Follow followRelationship = new Follow(follower, following)
        followService.save(followRelationship)
    }

    def unfollowUser(String followerId, String followingId) {
        User follower = userRepository.findById(followerId).orElseThrow { new Exception("Follower not found.") }
        User following = userRepository.findById(followingId).orElseThrow { new Exception("User to follow not found.") }
        Follow followRelationship = followService.findByFollowerIdAndFollowingId(follower, following)
        if (followRelationship) {
            followService.delete(followRelationship)
        } else {
            throw new Exception("Follow relationship not found.")
        }
    }

    def getUserFeed(String userId) {
        return postService.findByAuthor(userRepository.findById(userId))
    }
}

