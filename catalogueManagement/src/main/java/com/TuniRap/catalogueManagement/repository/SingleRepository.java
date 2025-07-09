package com.TuniRap.catalogueManagement.repository;

import com.TuniRap.catalogueManagement.model.Single;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleRepository extends MongoRepository<Single, String> {
    // Ajoutez ici des méthodes personnalisées si besoin
} 