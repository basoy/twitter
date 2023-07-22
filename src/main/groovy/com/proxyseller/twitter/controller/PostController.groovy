package com.proxyseller.twitter.controller

import com.proxyseller.twitter.dto.CommentRequest
import com.proxyseller.twitter.dto.PostCreateRequest
import com.proxyseller.twitter.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController {
    @Autowired
    private PostService postService

    @PostMapping
    def createPost(@RequestBody PostCreateRequest postRequest) {
        return postService.createPost(postRequest.content, postRequest.userId)
    }

    @PutMapping("/{postId}")
    def updatePost(@PathVariable String postId, @RequestBody def postRequest) {
        return postService.updatePost(postId, postRequest.content)
    }

    @DeleteMapping("/{postId}")
    def deletePost(@PathVariable String postId) {
        return postService.deletePost(postId)
    }

    @PostMapping("/{postId}/comment")
    def commentOnPost(@PathVariable String postId, @RequestBody CommentRequest commentRequest) {
        String userId = commentRequest.userId
        String content = commentRequest.content

        postService.commentOnPost(postId, userId, content)
    }

    @GetMapping("/{postId}/comment")
    def commentOnPost(@PathVariable String postId) {
        postService.getPostComments(postId)
    }
}