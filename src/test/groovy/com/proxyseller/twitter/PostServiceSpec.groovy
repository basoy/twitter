package com.proxyseller.twitter

import com.proxyseller.twitter.entity.Comment
import com.proxyseller.twitter.entity.Like
import com.proxyseller.twitter.entity.Post
import com.proxyseller.twitter.entity.User
import com.proxyseller.twitter.repository.CommentRepository
import com.proxyseller.twitter.repository.LikeRepository
import com.proxyseller.twitter.repository.PostRepository
import com.proxyseller.twitter.repository.UserRepository
import com.proxyseller.twitter.service.PostService
import com.proxyseller.twitter.service.UserService
import spock.lang.Specification
import spock.lang.Subject

class PostServiceSpec extends Specification {

    @Subject
    PostService postService

    PostRepository postRepository = Mock()
    UserRepository userRepository = Mock()
    CommentRepository commentRepository = Mock()
    LikeRepository likeRepository = Mock()
    UserService userService = Mock()

    def setup() {
        postService = new PostService(postRepository, userRepository, commentRepository, likeRepository, userService)
    }

    def "createPost() should create a new post with the provided details"() {
        given:
        String content = "Test post content"
        String authorId = "author123"
        User author = new User("testUser", "test@example.com", "testPassword", null)
        Post savedPost = new Post(content, author)

        userService.getUserById(authorId) >> author
        postRepository.save(_ as Post) >> savedPost

        when:
        Post result = postService.createPost(content, authorId)

        then:
        1 * userService.getUserById(authorId) >> author
        1 * postRepository.save(_ as Post) >> { Post post -> post }

        result.getContent() == content
        result.getAuthor() == author
    }

    def "getPostById() should return the correct post for the given postId"() {
        given:
        String postId = "post123"
        String content = "Test post content"
        String authorId = "author123"
        User author = new User("testUser", "test@example.com", "testPassword", null)
        Post expectedPost = new Post(content, author)
        expectedPost.id = postId

        postRepository.findById(postId) >> Optional.of(expectedPost)

        when:
        Post result = postService.getPostById(postId)

        then:
        1 * postRepository.findById(postId) >> Optional.of(expectedPost)
        result == expectedPost
    }

    def "updatePost() should update the post with the new content"() {
        given:
        String postId = "post123"
        String newContent = "Updated post content"
        Post existingPost = new Post("Old content", new User("author", "author@example.com", "password", null))
        existingPost.id = postId

        postRepository.findById(postId) >> Optional.of(existingPost)
        postRepository.save(_ as Post) >> existingPost

        when:
        Post result = postService.updatePost(postId, newContent)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.save(existingPost) >> existingPost
        result.getContent() == newContent
    }

    def "deletePost() should delete the post with the given postId"() {
        given:
        String postId = "post123"

        when:
        postService.deletePost(postId)

        then:
        1 * postRepository.deleteById(postId)
    }
}