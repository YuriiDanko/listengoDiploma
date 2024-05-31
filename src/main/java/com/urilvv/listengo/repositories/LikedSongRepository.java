package com.urilvv.listengo.repositories;

import com.urilvv.listengo.models.MusicRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedSongRepository extends JpaRepository<MusicRecommendation,String> {
    List<MusicRecommendation> getAllByUserId(String userId);
}
