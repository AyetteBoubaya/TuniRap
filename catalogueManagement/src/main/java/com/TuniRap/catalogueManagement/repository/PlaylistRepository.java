package com.TuniRap.catalogueManagement.repository;

import com.TuniRap.catalogueManagement.dto.responses.PlaylistResponse;
import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.model.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlaylistRepository extends MongoRepository<Playlist,String> {
    List<Playlist> findByName(String title);

    List<Playlist> findByArtistId(String artistId);


    //List<Playlist> findByArtistId(String artistId);
}
