package com.urilvv.listengo.services;

import com.urilvv.listengo.models.MusicRecommendation;

import java.util.List;

public interface MusicRecommendationService {
    List<MusicRecommendation> getAllByUserId(String userId);
    void saveAnswer(MusicRecommendation musicRecommendation);
}
