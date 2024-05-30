package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urilvv.listengo.models.securityModels.request.ChatGPTReq;
import com.urilvv.listengo.models.securityModels.response.ChatGPTRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class AIController {

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String chatGPTUrl;
    @Value("${openai.api.key}")
    private String chatGPTKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AIController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/recommendationAI")
    public String getResponse(@RequestParam("prompt") String prompt) throws JsonProcessingException {
        ChatGPTReq request = new ChatGPTReq(model, prompt);
        String requestJson = objectMapper.writeValueAsString(request);
        System.out.println("Request JSON: " + requestJson);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + chatGPTKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<ChatGPTRes> responseEntity = restTemplate.postForEntity(chatGPTUrl, entity, ChatGPTRes.class);
        ChatGPTRes response = responseEntity.getBody();

        return response.getChoices().get(0).getMessage().getContent();
    }

}
