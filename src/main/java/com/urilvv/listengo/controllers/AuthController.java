package com.urilvv.listengo.controllers;

import com.urilvv.listengo.dto.UserDto;
import com.urilvv.listengo.jwt.jwtUtils.JwtBuilderClass;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.mappers.UserMapper;
import com.urilvv.listengo.models.securityModels.request.LoginReq;
import com.urilvv.listengo.models.securityModels.request.RegisterReq;
import com.urilvv.listengo.models.securityModels.response.ErrorRes;
import com.urilvv.listengo.models.securityModels.response.LoginRes;
import com.urilvv.listengo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtBuilderClass jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtBuilderClass jwtUtil, UserService userService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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
    public ResponseEntity loginUser(@RequestBody LoginReq loginReq){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            String username = authentication.getName();
            UserDto userDto = UserMapper.mapToDto(userService.searchUser(username).get());
            String token = jwtUtil.createToken(userDto);
            LoginRes loginRes = new LoginRes(username,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            e.printStackTrace();
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
