package com.TuniRap.catalogueManagement.elasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.TuniRap.catalogueManagement.elasticSearch.model.AlbumDocument;
import com.TuniRap.catalogueManagement.elasticSearch.repository.AlbumSearchRepository;
import com.TuniRap.catalogueManagement.model.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumSearchService {

    private final AlbumSearchRepository albumSearchRepository;
    private final ElasticsearchClient elasticsearchClient;

    /**
     * Mapping Mongo Album vers Elasticsearch AlbumDocument
     */
    public AlbumDocument mapToDocument(Album album) {
        return AlbumDocument.builder()
                .albumId(album.getAlbumId())
                .title(album.getTitle())
                .releaseDate(album.getReleaseDate())
                .cover(album.getCover())
                .genres(album.getGenres())
                .build();
    }

    /**
     * Indexer un album dans Elasticsearch
     */
    public void indexAlbum(Album album) {
        AlbumDocument document = mapToDocument(album);
        albumSearchRepository.save(document);
    }

    /**
     * Rechercher des albums par titre
     */
    public List<AlbumDocument> searchByTitle(String query) {
        try {
            SearchResponse<AlbumDocument> response = elasticsearchClient.search(s -> s
                            .index("albums")
                            .query(q -> q
                                    .match(m -> m
                                            .field("title")
                                            .query(query)
                                    )
                            ),
                    AlbumDocument.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la recherche par titre", e);
        }
    }

    /**
     * Rechercher des albums par titre et genre
     */
    public List<AlbumDocument> searchByGenreAndTitle(String title, String genre) {
        try {
            SearchResponse<AlbumDocument> response = elasticsearchClient.search(s -> s
                            .index("albums")
                            .query(q -> q
                                    .bool(b -> b
                                            .must(
                                                    m1 -> m1.match(m -> m.field("title").query(title))
                                            )
                                    )
                            ),
                    AlbumDocument.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la recherche combinée", e);
        }
    }

    /**
     * Supprimer un album de l'index Elasticsearch
     */
    public void deleteFromIndex(String albumId) {
        albumSearchRepository.deleteById(albumId);
    }
}
