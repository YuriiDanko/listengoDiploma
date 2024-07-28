package com.urilvv.listengo.services;

import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.repositories.UserRepository;
import com.urilvv.listengo.services.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setup(){
        userService = new UserServiceImpl(userRepository);
        testUser = new User();
        testUser.setUserName("Urilvv");
        testUser.setEmail("urilvv1@gmail.com");
        testUser.setPassword("123");
        testUser.setPlaylists(new HashSet<>());
    }

    @Test
    public void userCreateTest(){
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        User userCreated = userService.createUser(testUser);

        assertNotNull(userCreated);
        assertEquals(userCreated.getUserName(), testUser.getUserName());
        assertEquals("123", userCreated.getPassword());
    }

    @Test
    public void searchUserById_ByUserNameTest(){
        when(userService.createUser(any(User.class))).thenReturn(testUser);
        when(userService.searchUser(any(String.class))).thenReturn(Optional.ofNullable(testUser));
        when(userService.searchById(any(String.class))).thenReturn(Optional.ofNullable(testUser));

        User userCreated = userService.createUser(testUser);
        User userIdSearched = userService.searchById("123").get();
        User userUserNameSearched = userService.searchUser(userCreated.getUserName()).get();

        assertNotNull(userCreated);
        assertEquals(userCreated, userIdSearched);
        assertEquals(userUserNameSearched, userIdSearched);
        assertEquals(userCreated, userUserNameSearched);
    }

    @Test
    public void saveUserTest(){
        Set<Playlist> playlists = testUser.getPlaylists();
        playlists.add(new Playlist());
        playlists.add(new Playlist());
        playlists.add(new Playlist());

        when(userService.searchUser(any(String.class))).thenReturn(Optional.ofNullable(testUser));

        testUser.setPlaylists(playlists);
        userService.saveUser(testUser);

        User savedUser = userService.searchUser(testUser.getUserName()).get();

        assertEquals(savedUser.getPlaylists().size(), playlists.size());
    }

}
