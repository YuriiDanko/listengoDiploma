package com.urilvv.listengo.services.serviceImpl;

import com.urilvv.listengo.models.MusicRecommendation;
import com.urilvv.listengo.repositories.LikedSongRepository;
import com.urilvv.listengo.services.MusicRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicRecServiceImpl implements MusicRecommendationService {

    private LikedSongRepository likedSongRepository;

    @Autowired
    public MusicRecServiceImpl(LikedSongRepository likedSongRepository) {
        this.likedSongRepository = likedSongRepository;
    }

    @Override
    public List<MusicRecommendation> getAllByUserId(String userId) {
        return likedSongRepository.getAllByUserId(userId);
    }

    @Override
    public void saveAnswer(MusicRecommendation musicRecommendation) {
        likedSongRepository.save(musicRecommendation);
    }

}
