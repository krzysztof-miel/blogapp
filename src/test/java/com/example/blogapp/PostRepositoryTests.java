package com.example.blogapp;

import com.example.blogapp.model.Post;
import com.example.blogapp.repository.PostRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostRepositoryTests {


    @Autowired
    private PostRepository postRepository;

    @Test
    public void PostRepository_Save_ReturnSavedPost() {
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();

        Post savedPost = postRepository.save(post);

        assertThat(savedPost).isNotNull();
        System.out.println(savedPost.getDate());
        assertThat(savedPost.getId()).isGreaterThan(0);
    }

    @Test
    public void PostRepository_FindAll_ReturnMoreThanOnePost(){
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();

        Post savedPost = postRepository.save(post);

        Post post2 = Post.builder()
                .title("Title test 2")
                .content("Content test 2").build();

        Post savedPost2 = postRepository.save(post2);

        List<Post> postsList = postRepository.findAll();

        assertThat(postsList).isNotNull();
        assertThat(postsList.size()).isEqualTo(2);

    }
    @Test
    public void PostRepository_FindById_ReturnSpecificPost(){
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();

        Post savedPost = postRepository.save(post);

        Post findPost = postRepository.findById(savedPost.getId()).get();

        assertThat(findPost).isNotNull();
        assertThat(findPost.getId()).isEqualTo(1);

    }

    @Test
    public void PostRepository_FindById_PostNotFind_ReturnEmpty(){
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();

        Post savedPost = postRepository.save(post);

        Optional<Post> findPost = postRepository.findById(savedPost.getId());
        assertThat(findPost).isPresent();

        postRepository.deleteById(savedPost.getId());

        Optional<Post> deletedPost = postRepository.findById(savedPost.getId());

        assertThat(deletedPost).isNotPresent();

    }



    @Test
    public void PostRepository_DeleteById_PostDeleted(){
        Post post = Post.builder()
                .title("Title test")
                .content("Content test").build();

        Post savedPost = postRepository.save(post);
        postRepository.deleteById(savedPost.getId());

        Optional<Post> findPost = postRepository.findById(savedPost.getId());

        assertThat(findPost).isEmpty();


    }
}

