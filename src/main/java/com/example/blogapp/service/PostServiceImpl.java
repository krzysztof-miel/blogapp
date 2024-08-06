package com.example.blogapp.service;

import com.example.blogapp.dto.PostDto;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.model.Post;
import com.example.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToPost(postDto);
        postRepository.save(post);

        return mapToPostDto(post);
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post: posts) {
            PostDto postModel = mapToPostDto(post);
            postDtos.add(postModel);
        }
        return postDtos;
    }

    @Override
    public PostDto getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            return mapToPostDto(optionalPost.get());
        }else {
            throw new ResourceNotFoundException("Post with id -> " + id + " not found");
        }
    }

    @Override
    public PostDto updatePostById(PostDto postDto ,Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){

            optionalPost.get().setTitle(postDto.getTitle());
            optionalPost.get().setContent(postDto.getContent());

            Post updatedPost = postRepository.save(optionalPost.get());

            return mapToPostDto(updatedPost);
        }else {
            throw new ResourceNotFoundException("Post with id -> " + id + " not found");
        }
    }

    @Override
    public void deletePostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            postRepository.delete(optionalPost.get());
        }else {
            throw new ResourceNotFoundException("Post with id -> " + id + " not found");
        }
    }


    private PostDto mapToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDate(post.getDate());

        return postDto;
    }

    private Post mapToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return post;
    }
}
