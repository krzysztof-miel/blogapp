package com.example.blogapp;

import com.example.blogapp.service.GPTModuleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class GPTModuleServiceTests {

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse response;

    @InjectMocks
    private GPTModuleService gptModuleService;

    @BeforeEach
    public void setUp() {
        // Inicjalizacja mocków
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        gptModuleService = new GPTModuleService(objectMapper, httpClient);


        gptModuleService.setOPEN_AI_URL("https://api.openai.com/v1/chat/completions");

        gptModuleService.setOPEN_AI_KEY("test-api-key");

    }


    @Test
    public void testGetResponse() throws IOException {
        String jsonResponse = "{\n" +
                "  \"id\": \"chatcmpl-9uEWLfT0GquFBdeS0xr3qwJ456aDH\",\n" +
                "  \"object\": \"chat.completion\",\n" +
                "  \"created\": 1723189213,\n" +
                "  \"model\": \"gpt-3.5-turbo-0125\",\n" +
                "  \"choices\": [\n" +
                "    {\n" +
                "      \"index\": 0,\n" +
                "      \"message\": {\n" +
                "        \"role\": \"assistant\",\n" +
                "        \"content\": \"Hi! How can I assist you today?\",\n" +
                "        \"refusal\": null\n" +
                "      },\n" +
                "      \"logprobs\": null,\n" +
                "      \"finish_reason\": \"stop\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 10,\n" +
                "    \"completion_tokens\": 9,\n" +
                "    \"total_tokens\": 19\n" +
                "  },\n" +
                "  \"system_fingerprint\": null\n" +
                "}";

        StringEntity entity = new StringEntity(jsonResponse, StandardCharsets.UTF_8);
        when(response.getEntity()).thenReturn(entity);

        when(httpClient.execute(any(HttpPost.class))).thenReturn(response);

        String result = gptModuleService.getResponse("Hello");

        Assertions.assertEquals("Hi! How can I assist you today?", result);

        verify(httpClient, times(1)).execute(any(HttpPost.class));
    }

    @Test
    public void testGetResponseJson() throws IOException {

        String jsonResponse = "{\n" +
                "  \"id\": \"chatcmpl-9uEWLfT0GquFBdeS0xr3qwJ456aDH\",\n" +
                "  \"object\": \"chat.completion\",\n" +
                "  \"created\": 1723189213,\n" +
                "  \"model\": \"gpt-3.5-turbo-0125\",\n" +
                "  \"choices\": [\n" +
                "    {\n" +
                "      \"index\": 0,\n" +
                "      \"message\": {\n" +
                "        \"role\": \"assistant\",\n" +
                "        \"content\": \"Hi! How can I assist you today?\",\n" +
                "        \"refusal\": null\n" +
                "      },\n" +
                "      \"logprobs\": null,\n" +
                "      \"finish_reason\": \"stop\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 10,\n" +
                "    \"completion_tokens\": 9,\n" +
                "    \"total_tokens\": 19\n" +
                "  },\n" +
                "  \"system_fingerprint\": null\n" +
                "}";

        StringEntity entity = new StringEntity(jsonResponse, StandardCharsets.UTF_8);
        when(response.getEntity()).thenReturn(entity);

        when(httpClient.execute(any(HttpPost.class))).thenReturn(response);

        String result = gptModuleService.getResponseJson("Hello");

        Assertions.assertEquals(jsonResponse, result);

        verify(httpClient, times(1)).execute(any(HttpPost.class));
    }


    @Test
    public void testGetResponseJson_ThrowsIOException() throws IOException {
        String expectedMessage = "Simulated IOException";
        when(httpClient.execute(any(HttpPost.class))).thenThrow(new IOException(expectedMessage));

        IOException thrownException = Assertions.assertThrows(IOException.class, () -> {
            gptModuleService.getResponseJson("Hello");
        });

        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
        verify(httpClient, times(1)).execute(any(HttpPost.class));

    }

}
