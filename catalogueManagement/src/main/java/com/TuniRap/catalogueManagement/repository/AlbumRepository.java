package com.TuniRap.catalogueManagement.repository;


import com.TuniRap.catalogueManagement.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends MongoRepository<Album,String> {

    Album findByTitle(String title);
    List<Album> findByArtistId(String artistId);
    List<Album> findByScrappedTrue();

}
