package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.dto.requests.AlbumRequest;
import com.TuniRap.catalogueManagement.dto.responses.AlbumResponse;

import java.util.List;

public interface AlbumService {

    AlbumResponse addAlbum(AlbumRequest albumRequest);
    AlbumResponse updateAlbum(String id, AlbumRequest albumRequest);
    void deleteAlbum(String id);
    AlbumResponse getAlbumById(String id);
    List<AlbumResponse> getAllAlbums();
    //List<AlbumResponse> getAlbumsByArtist(String id);
}
