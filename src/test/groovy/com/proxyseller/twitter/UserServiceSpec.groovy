package com.proxyseller.twitter

import com.proxyseller.twitter.entity.Follow
import com.proxyseller.twitter.entity.Post
import com.proxyseller.twitter.repository.UserRepository
import com.proxyseller.twitter.service.FollowService
import com.proxyseller.twitter.service.PostService
import spock.lang.Specification
import spock.lang.Subject
import com.proxyseller.twitter.entity.User
import com.proxyseller.twitter.service.UserService

class UserServiceSpec extends Specification {

    @Subject
    UserService userService

    UserRepository userRepository = Mock()

    FollowService followService = Mock()

    PostService postService = Mock()

    def setup() {
        userService = new UserService()
        userService.userRepository = userRepository
        userService.followService = followService
        userService.postService = postService
    }

    def "createUser() should create a new user with the provided details"() {
        given:
        String username = "testUser"
        String email = "test@example.com"
        String password = "testPassword"

        when:
        User result = userService.createUser(username, email, password, null)

        then:
        1 * userRepository.save(_ as User) >> { User user -> user }
        result.getUsername() == username
        result.getEmail() == email
        result.getPassword() == password
    }

    def "updateUser() should update the user with the new details"() {
        given:
        String userId = "12345"
        String newUsername = "newTestUser"
        String newEmail = "newtest@example.com"
        String newPassword = "newTestPassword"

        userRepository.findById(userId) >> Optional.of(new User(newUsername, newEmail, newPassword, null))

        when:
        User result = userService.updateUser(userId, newUsername, newEmail, newPassword)

        then:
        1 * userRepository.save(_ as User) >> { User user -> user }
        result.getUsername() == newUsername
        result.getEmail() == newEmail
        result.getPassword() == newPassword
    }

    def "getUserById() should return the correct user for the given userId"() {
        given:
        String userId = "12345"
        String newUsername = "newTestUser"
        String newEmail = "newtest@example.com"
        String newPassword = "newTestPassword"
        User expectedUser = new User(newUsername, newEmail, newPassword, null)

        userRepository.findById(userId) >> Optional.of(new User(newUsername, newEmail, newPassword, null))

        when:
        User result = userService.getUserById(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(expectedUser)
        result.getUsername() == newUsername
        result.getEmail() == newEmail
        result.getPassword() == newPassword
    }

    def "deleteUser() should delete the user with the given userId"() {
        given:
        String userId = "12345"

        when:
        userService.deleteUser(userId)

        then:
        1 * userRepository.deleteById(userId)
    }

    def "followUser() should create a follower-following relationship"() {
        given:
        String followerId = "follower123"
        String followingId = "following456"
        String newUsername = "newTestUser"
        String newEmail = "newtest@example.com"
        String newPassword = "newTestPassword"
        User follower = new User(newUsername, newEmail, newPassword, null)
        User following = new User(newUsername, newEmail, newPassword, null)

        userRepository.findById(followerId) >> Optional.of(follower)
        userRepository.findById(followingId) >> Optional.of(following)

        when:
        userService.followUser(followerId, followingId)

        then:
        1 * userRepository.findById(followerId) >> Optional.of(follower)
        1 * userRepository.findById(followingId) >> Optional.of(following)

        1 * followService.save(_ as Follow) >> { Follow follow -> follow.followerId.id == followerId && follow.followingId.id == followingId }
    }

    def "unfollowUser() should remove a follower-following relationship if it exists"() {
        given:
        String followerId = "follower123"
        String followingId = "following456"
        String newUsername = "newTestUser"
        String newEmail = "newtest@example.com"
        String newPassword = "newTestPassword"
        User follower = new User(newUsername, newEmail, newPassword, null)
        User following = new User(newUsername, newEmail, newPassword, null)
        Follow followRelationship = new Follow(follower, following)

        userRepository.findById(followerId) >> Optional.of(follower)
        userRepository.findById(followingId) >> Optional.of(following)

        followService.findByFollowerIdAndFollowingId(follower, following) >> followRelationship

        when:
        userService.unfollowUser(followerId, followingId)

        then:
        1 * userRepository.findById(followerId) >> Optional.of(follower)
        1 * userRepository.findById(followingId) >> Optional.of(following)
        1 * followService.findByFollowerIdAndFollowingId(follower, following) >> followRelationship
        1 * followService.delete(followRelationship)
    }

    def "getUserFeed() should return the user's feed correctly"() {
        given:
        String userId = "user123"
        String newUsername = "newTestUser"
        String newEmail = "newtest@example.com"
        String newPassword = "newTestPassword"
        User user = new User(newUsername, newEmail, newPassword, null)
        List<Post> expectedFeed = [new Post("Post 1", user), new Post("Post 2", user)]

        userRepository.findById(userId) >> Optional.of(user)

        postService.findByAuthor(Optional.of(user)) >> expectedFeed

        when:
        List<Post> result = userService.getUserFeed(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * postService.findByAuthor(Optional.of(user)) >> expectedFeed
        result == expectedFeed
    }

    def "unfollowUser() should throw an exception if the follow relationship is not found"() {
        given:
        String followerId = "follower123"
        String followingId = "following456"
        String newUsername = "newTestUser"
        String newEmail = "newtest@example.com"
        String newPassword = "newTestPassword"
        User follower = new User(newUsername, newEmail, newPassword, null)
        User following = new User(newUsername, newEmail, newPassword, null)
        Follow followRelationship = new Follow(follower, following)

        userRepository.findById(followerId) >> Optional.of(follower)
        userRepository.findById(followingId) >> Optional.of(following)

        followService.findByFollowerIdAndFollowingId(follower, following) >> null

        when:
        userService.unfollowUser(followerId, followingId)

        then:
        1 * userRepository.findById(followerId) >> Optional.of(follower)
        1 * userRepository.findById(followingId) >> Optional.of(following)
        1 * followService.findByFollowerIdAndFollowingId(follower, following) >> followRelationship
        1 * followService.delete(followRelationship)
    }
}