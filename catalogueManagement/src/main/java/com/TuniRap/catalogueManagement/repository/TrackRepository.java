package com.TuniRap.catalogueManagement.repository;

import com.TuniRap.catalogueManagement.dto.responses.TrackResponse;
import com.TuniRap.catalogueManagement.model.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TrackRepository extends MongoRepository<Track,String> {

    Track findByTitle(String title);
    List<Track> findByAlbumId(String albumId);

    List<Track> findByArtistId(String artistId);

    List<Track> findCollaborationsByArtistId(String artistId);
    //List<Track> findByArtistId(String artistId);

}
