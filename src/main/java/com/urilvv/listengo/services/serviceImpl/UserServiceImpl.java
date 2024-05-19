package com.urilvv.listengo.services.serviceImpl;

import com.urilvv.listengo.dto.UserDto;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.mappers.UserMapper;
import com.urilvv.listengo.repositories.UserRepository;
import com.urilvv.listengo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(User user) {
        userRepository.save(user);
        return UserMapper.mapToDto(user);
    }

    @Override
    public Optional<User> searchUser(String username) {
        return userRepository.findByUserName(username);
    }
}
