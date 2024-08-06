package com.example.blogapp.controller;

import com.example.blogapp.dto.PostDto;
import com.example.blogapp.service.GPTModuleService;
import com.example.blogapp.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/posts")
@RestController
public class PostController {
    private PostService postService;
    private GPTModuleService chatService;
    @Autowired
    public PostController(PostService postService, GPTModuleService chatService) {
        this.postService = postService;
        this.chatService = chatService;
    }

    @PostMapping("/new")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getSortedPosts() {
        List<PostDto> posts = postService.getAllPostSortedByDate();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.updatePostById(postDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
    }


    @PostMapping("/post-from-chat")
    public ResponseEntity<PostDto> createPostFromCharGPT() {
        try {
            PostDto postDto = chatService.createPostFromChatGPT();
            PostDto createdPost = postService.createPost(postDto);

            return  new ResponseEntity<>(createdPost, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
