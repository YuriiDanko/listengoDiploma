package com.urilvv.listengo.repositories;

import com.urilvv.listengo.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    @Query("SELECT p FROM Playlist p WHERE p.playlistName ILIKE %:parameter% AND p.creator != :creatorId AND p.isPrivate != true")
    List<Playlist> findByPlaylistNameContainingIgnoreCaseAndCreatorNotIn(@Param("parameter") String parameter, @Param("creatorId") String creatorId);
}
