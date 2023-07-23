package com.proxyseller.twitter.service

import com.proxyseller.twitter.entity.Comment
import com.proxyseller.twitter.entity.Like
import com.proxyseller.twitter.entity.Post
import com.proxyseller.twitter.entity.User
import com.proxyseller.twitter.repository.CommentRepository
import com.proxyseller.twitter.repository.LikeRepository
import com.proxyseller.twitter.repository.PostRepository
import com.proxyseller.twitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {
    @Autowired
    private PostRepository postRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private CommentRepository commentRepository

    @Autowired
    private LikeRepository likeRepository

    @Autowired
    private UserService userService

    def createPost(String content, String author) {
        Post newPost = new Post(content, userService.getUserById(author))
        return postRepository.save(newPost)
    }

    def getPostById(String postId) {
        return postRepository.findById(postId).orElse(null)
    }

    def updatePost(String postId, String content) {
        def postToUpdate = getPostById(postId)
        if (postToUpdate) {
            postToUpdate.content = content
            return postRepository.save(postToUpdate)
        }
        return null
    }

    def deletePost(String postId) {
        postRepository.deleteById(postId)
    }


    def commentOnPost(String postId, String userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow { new Exception("Post not found.") }
        User user = userRepository.findById(userId).orElseThrow { new Exception("User not found.") }

        Comment comment = new Comment(post, user, content)
        commentRepository.save(comment)
        post.addComment(comment)
        postRepository.save(post)
    }

    def getPostComments(String postId) {
        Post post = postRepository.findById(postId).orElse(null)
        return post != null ? post.getComments() : Collections.emptyList()
    }

    def likePost(String postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow { new Exception("Post not found.") }
        User user = userRepository.findById(userId).orElseThrow { new Exception("User not found.") }

        Like like = new Like(post, user)
        likeRepository.save(like)
        post.addLike(like)
        postRepository.save(post)
    }

    def unlikePost(String postId, String userId) {
        Like like = postRepository.findByIdAndAuthorId(postId, userId).getLikes().get(0)
        if (like) {
            Post post = postRepository.findById(postId).orElseThrow { new Exception("Post not found.") }
            post.removeLike(like)
            postRepository.save(post)
        } else {
            throw new Exception("Like not found.")
        }
    }

    def findByAuthor(def user) {
        return postRepository.findByAuthor(user)
    }
}