package com.example.blogapp;

import com.example.blogapp.dto.PostDto;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.model.Post;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.service.PostServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;


    @Test
    public void PostService_CreatePost_ReturnPostDto() {
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();

        PostDto postDTO = PostDto.builder()
                .title("Title test")
                .content("Content test").build();

        when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        PostDto savedPost = postService.createPost(postDTO);

        Assertions.assertThat(savedPost).isNotNull();
        Assertions.assertThat(savedPost.getTitle()).isEqualTo("Title test");

    }

    @Test
    public void PostService_GetAllPosts_ReturnListOfPostDtos() {
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();
        Post post2 = Post.builder()
                .title("Title test2")
                .content("Content test2").build();

        List<Post> posts = new ArrayList<>();
        posts.add(post);
        posts.add(post2);

        when(postRepository.findAll()).thenReturn(posts);

        List<PostDto> postDtos = postService.getAllPosts();

        Assertions.assertThat(postDtos).hasSize(2);
        Assertions.assertThat(postDtos.get(0).getTitle()).isEqualTo("Title test");
        Assertions.assertThat(postDtos.get(1).getTitle()).isEqualTo("Title test2");

    }

    @Test
    public void PostService_GetPostById_ReturnPost() {
        Post post = Post.builder()
                .id(1L)
                .title("Title test")
                .content("Content test").build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDto postDto = postService.getPostById(1L);

        Assertions.assertThat(postDto).isNotNull();
        Assertions.assertThat(postDto.getId()).isEqualTo(1L);
    }
    @Test
    public void PostService_GetPostById_ThrowResourceNotFoundException() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable thrown = Assertions.catchThrowable(() -> {
            postService.getPostById(1L);
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post with id -> 1 not found");
    }

    @Test
    public void PostService_UpdatePostById_ReturnUpdatedPostDto() {
        Post post = Post.builder()
                .id(1L)
                .title("Title test")
                .content("Content test").build();

        Post updatedPost = Post.builder()
                .id(1L)
                .title("New title test")
                .content("New content test").build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(Mockito.any(Post.class))).thenReturn(updatedPost);

        PostDto postDto = PostDto.builder()
                .title("New title test")
                .content("New content test").build();

        PostDto updatedPostDto = postService.updatePostById(postDto, 1L);

        Assertions.assertThat(updatedPostDto).isNotNull();
        Assertions.assertThat(updatedPostDto.getTitle()).isEqualTo("New title test");

    }

    @Test
    public void PostService_UpdatePostById_ThrowResourceNotFoundException() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        PostDto postDTO = PostDto.builder()
                .title("Title test")
                .content("Content test").build();

        Throwable thrown = Assertions.catchThrowable(() -> {
            postService.updatePostById(postDTO, 1L);
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post with id -> 1 not found");
    }

    @Test
    public void PostService_DeletePostById_PostDeleted() {
        Post post = Post.builder()
                .id(1L)
                .title("Title test")
                .content("Content test").build();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        postService.deletePostById(1L);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void PostService_DeletePostById_ThrowResourceNotFoundException() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable thrown = Assertions.catchThrowable(() -> {
            postService.deletePostById(1L);
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post with id -> 1 not found");
    }
}
