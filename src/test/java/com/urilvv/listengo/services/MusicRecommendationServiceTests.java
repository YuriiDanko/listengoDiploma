package com.urilvv.listengo.services;

import com.urilvv.listengo.models.MusicRecommendation;
import com.urilvv.listengo.repositories.LikedSongRepository;
import com.urilvv.listengo.services.serviceImpl.MusicRecServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicRecommendationServiceTests {

    @Mock
    private LikedSongRepository likedSongRepository;
    @InjectMocks
    private MusicRecServiceImpl musicRecService;

    private MusicRecommendation musicRecommendation;

    @BeforeEach
    public void setup(){
        musicRecService = new MusicRecServiceImpl(likedSongRepository);
        musicRecommendation = new MusicRecommendation();
        musicRecommendation.setId("UUID");
        musicRecommendation.setLiked(true);
        musicRecommendation.setSongName("One More Night");
        musicRecommendation.setUserId("User UUID");
    }

    @Test
    public void saveMusicRecommendationTest(){
        assertAll(() -> musicRecService.saveAnswer(musicRecommendation));
    }

    @Test
    public void getAllRecsByUserId(){
        when(musicRecService.getAllByUserId(any(String.class))).thenReturn(List.of(musicRecommendation));

        List<MusicRecommendation> musicRecommendations = musicRecService.getAllByUserId("UUID");

        assertNotNull(musicRecommendations);
        assertEquals(musicRecommendations.size(), 1);
    }

}
