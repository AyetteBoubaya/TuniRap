package com.TuniRap.catalogueManagement.elasticSearch.repository;

import com.TuniRap.catalogueManagement.elasticSearch.model.AlbumDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlbumSearchRepository extends ElasticsearchRepository<AlbumDocument, String> {
     List<AlbumDocument> findByTitleContaining(String title);

}
