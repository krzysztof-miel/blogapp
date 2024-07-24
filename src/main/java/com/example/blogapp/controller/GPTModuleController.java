package com.example.blogapp.controller;

import com.example.blogapp.service.GPTModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController

public class GPTModuleController {

    @Autowired
    private GPTModuleService service;

    @PostMapping("/ask")
    public ResponseEntity<String> askChatGPT(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        try {
            log.info("searchChatGPT Started query: " + prompt);
            String response = service.getResponse(prompt);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/ask")
//    public ResponseEntity<PostFromChatGPT> askChatGPT() {
//        try {
//
//            PostFromChatGPT postFromChatGPT = service.createPostFromChatGPT();
//
//            return  new ResponseEntity<>(postFromChatGPT, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
