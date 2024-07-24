package com.example.blogapp.service;

import com.example.blogapp.dto.PostDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class GPTModuleService {
    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY;

    @Value("${OPEN_AI_URL}")
    private String OPEN_AI_URL;

    public String getResponse(String prompt) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(OPEN_AI_URL);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Authorization", "Bearer " + OPEN_AI_KEY);

        String json = String.format("{\"model\": \"gpt-3.5-turbo\"," +
                " \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]," +
                " \"max_tokens\": 1000}",
                prompt.replaceAll("\"", "\\\""));

        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

        CloseableHttpResponse response = httpClient.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        httpClient.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        String content = rootNode.path("choices").get(0).path("message").path("content").asText();

        System.out.println(content);

        return content;
    }

    public PostDto createPostFromChatGPT() throws IOException {
        PostDto postDto = new PostDto();

        postDto.setTitle(generatePostTitle());
        postDto.setContent(generatePostContent(postDto.getTitle()));

        return postDto;
    }


    private String generatePostTitle() throws IOException {
        return getResponse("w maksymalnie pięciu słowach wymyśl dowolny temat ciekawostki");

    }

    private String generatePostContent(String topic) throws IOException {
        String prompt = "wymyśl ciekawostę na podany temat: " + topic;
        return getResponse(prompt);

    }


}
