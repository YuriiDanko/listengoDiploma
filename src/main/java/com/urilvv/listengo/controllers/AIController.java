package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urilvv.listengo.json.jsonUtils.Parser;
import com.urilvv.listengo.models.MusicRecommendation;
import com.urilvv.listengo.models.httpModels.request.ChatGPTReq;
import com.urilvv.listengo.models.httpModels.response.ChatGPTRes;
import com.urilvv.listengo.services.serviceImpl.MusicRecServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AIController {

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String chatGPTUrl;
    @Value("${openai.api.key}")
    private String chatGPTKey;
    private String accessToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final MusicRecServiceImpl musicRecService;

    @Autowired
    public AIController(RestTemplate restTemplate, ObjectMapper objectMapper, String accessToken, MusicRecServiceImpl musicRecService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.accessToken = accessToken;
        this.musicRecService = musicRecService;
    }

    @GetMapping("/recommendationAI/{userId}")
    public ResponseEntity getResponse(@PathVariable("userId") String userId, HttpServletRequest requestContext) throws JsonProcessingException {
        List<MusicRecommendation> userRecOptions = musicRecService.getAllByUserId(userId);
        List<String> liked = userRecOptions.stream().filter(MusicRecommendation::isLiked).map(MusicRecommendation::getSongName).toList();
        List<String> disliked = userRecOptions.stream().filter(song -> !song.isLiked()).map(MusicRecommendation::getSongName).toList();

        String promptString;
        if(liked.isEmpty()){
            promptString = "Give me a song recommendation but don't repeat this one " + disliked;
        } else {
            promptString = "Give me a song recommendation similar to this songs " + liked + " but do not repeat this songs and do not recommend this one " + disliked;
        }

        ChatGPTReq request = new ChatGPTReq(model, promptString);
        String requestJson = objectMapper.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + chatGPTKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<ChatGPTRes> responseEntity = restTemplate.postForEntity(chatGPTUrl, entity, ChatGPTRes.class);
        String songRecommended = responseEntity.getBody().getChoices().get(0).getMessage().getContent();

        String requestUrl = "http://localhost:8080/search/" + songRecommended + "/noId";

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + requestContext.getHeader("Authorization").substring(7));
        entity = new HttpEntity<>(headers);

        ResponseEntity<String> trackResponse = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
        JsonNode jsonNode = Parser.parseJson(trackResponse.getBody());

        requestUrl = "http://localhost:8080/tracks/" + jsonNode.get("tracks").get(0).get("trackId").toString().replace("\"", "");
        ResponseEntity<String> trackJson = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(trackJson.getBody());
    }

}
