package com.urilvv.listengo.controllers;

import com.urilvv.listengo.dto.UserDto;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.mappers.UserMapper;
import com.urilvv.listengo.models.securityModels.request.LoginReq;
import com.urilvv.listengo.models.securityModels.request.RegisterReq;
import com.urilvv.listengo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterReq registerReq){
        User user = new User();
        user.setEmail(registerReq.getEmail());
        user.setUserName(registerReq.getUsername());
        user.setPassword(registerReq.getPassword());
        UserDto userDto = userService.createUser(user);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginReq loginReq){
        User user = userService.searchUser(loginReq.getUsername()).get();

        UserDto userDto = UserMapper.mapToDto(user);

        return ResponseEntity.ok(userDto);
    }

}
