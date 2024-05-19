package com.urilvv.listengo.services;

import com.urilvv.listengo.dto.UserDto;
import com.urilvv.listengo.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    UserDto createUser(User user);
    Optional<User> searchUser(String username);

}
