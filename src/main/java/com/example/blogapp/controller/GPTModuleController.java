package com.example.blogapp.controller;

import com.example.blogapp.service.GPTModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class GPTModuleController {

    private final GPTModuleService service;

    @Autowired
    public GPTModuleController(GPTModuleService service) {
        this.service = service;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askChatGPT(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        try {
            log.info("askChatGPT completed query: {}", prompt);
            String response = service.getResponse(prompt);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error while processing query: {}", prompt, e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
