package com.example.blogapp.service;

import com.example.blogapp.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto);
    PostDto createPostFromChatGPT();

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePostById(PostDto postDto, Long id);

    void deletePostById(Long id);

}
