package com.urilvv.listengo.controllers;

import com.urilvv.listengo.jwt.jwtUtils.JwtBuilderClass;
import com.urilvv.listengo.models.MusicRecommendation;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.httpModels.request.SaveAnswerReq;
import com.urilvv.listengo.models.mappers.UserMapper;
import com.urilvv.listengo.models.httpModels.response.LoginRegisterRes;
import com.urilvv.listengo.services.UserService;
import com.urilvv.listengo.services.serviceImpl.MusicRecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtBuilderClass jwtUtil;
    private final MusicRecServiceImpl musicRecService;

    @Autowired
    public UserController(UserService userService, JwtBuilderClass jwtUtil, MusicRecServiceImpl musicRecService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.musicRecService = musicRecService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(UserMapper.mapToDto(userService.searchById(userId).get()));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity editUser(@PathVariable("userId") String userId,
                                   @RequestParam("username") String username){
        User user = userService.searchById(userId).get();
        user.setUserName(username);
        userService.saveUser(user);

        String jwtToken = jwtUtil.createToken(UserMapper.mapToDto(user));

        LoginRegisterRes loginRegisterRes = new LoginRegisterRes(username, jwtToken, userId, "empty");

        return ResponseEntity.ok(loginRegisterRes);
    }

    @PostMapping("/user/save-answer/{userId}")
    public ResponseEntity saveAnswer(@PathVariable("userId") String userId, @RequestBody SaveAnswerReq request){
        musicRecService.saveAnswer(new MusicRecommendation(request.getSongName(), request.isLiked(), userId));

        return ResponseEntity.ok("ok");
    }

}
    