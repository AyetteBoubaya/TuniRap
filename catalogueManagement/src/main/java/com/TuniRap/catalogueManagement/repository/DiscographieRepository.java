package com.TuniRap.catalogueManagement.repository;

import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.model.Discographie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscographieRepository extends MongoRepository<Discographie, String > {

    List<Discographie> findAllByDiscographieId(String discographieId);
    //Discographie findByArtistId(String artistId);

}
