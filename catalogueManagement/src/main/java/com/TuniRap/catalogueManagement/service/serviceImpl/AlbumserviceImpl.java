package com.TuniRap.catalogueManagement.service.serviceImpl;


import com.TuniRap.catalogueManagement.dto.requests.AlbumRequest;
import com.TuniRap.catalogueManagement.dto.responses.AlbumResponse;
import com.TuniRap.catalogueManagement.exceptions.AlreadyExistException;
import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.repository.AlbumRepository;
import com.TuniRap.catalogueManagement.service.AlbumService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class AlbumserviceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public AlbumResponse addAlbum(AlbumRequest albumRequest) {

        Album album = new Album();
        album.setAlbumId(albumRequest.getAlbumId());
        album.setTitle(albumRequest.getTitle());
        album.setReleaseDate(albumRequest.getReleaseDate());
        album.setCover(albumRequest.getCover());
        album.setTrackIds(albumRequest.getTrackIds());
        album.setGenres(albumRequest.getGenres());
        album.setTracks(albumRequest.getTracks());
        album.setScrapped(false);

        Album savedAlbum = albumRepository.save(album);

        return new AlbumResponse(savedAlbum.getAlbumId(), savedAlbum.getTitle(), savedAlbum.getReleaseDate(),
                savedAlbum.getCover(), savedAlbum.getTrackIds(), savedAlbum.getGenres(),savedAlbum.getTracks());
    }

    @Override
    public AlbumResponse updateAlbum(String id, AlbumRequest albumRequest) {
        Optional<Album> albumOpt = albumRepository.findById(id);
        if (!albumOpt.isPresent()) {
            throw new AlreadyExistException("Album not found with id: " + id);
        }

        Album album = albumOpt.get();
        album.setTitle(albumRequest.getTitle());
        album.setReleaseDate(albumRequest.getReleaseDate());
        album.setCover(albumRequest.getCover());
        album.setTrackIds(albumRequest.getTrackIds());
        album.setGenres(albumRequest.getGenres());
        album.setTracks(albumRequest.getTracks());


        Album updatedAlbum = albumRepository.save(album);

        return new AlbumResponse(updatedAlbum.getAlbumId(), updatedAlbum.getTitle(), updatedAlbum.getReleaseDate(),
                updatedAlbum.getCover(), updatedAlbum.getTrackIds(), updatedAlbum.getGenres(),updatedAlbum.getTracks());
    }

    @Override
    public void deleteAlbum(String id) {
        if (!albumRepository.existsById(id)) {
            throw new AlreadyExistException("Album not found with id: " + id);
        }

        albumRepository.deleteById(id);
    }

    @Override
    public AlbumResponse getAlbumById(String id) {
        Optional<Album> albumOpt = albumRepository.findById(id);
        if (!albumOpt.isPresent()) {
            throw new AlreadyExistException("Album not found with id: " + id);
        }

        // Retourner l'album sous forme d'AlbumResponse
        Album album = albumOpt.get();
        return new AlbumResponse(album.getAlbumId(),album.getTitle(), album.getReleaseDate(),
                album.getCover(), album.getTrackIds(), album.getGenres(),album.getTracks());
    }

    @Override
    public List<AlbumResponse> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();

        return albums.stream()
                .map(album -> new AlbumResponse(album.getAlbumId(),album.getTitle(), album.getReleaseDate(),
                        album.getCover(), album.getTrackIds(), album.getGenres(),album.getTracks()))
                .collect(Collectors.toList());
    }


    /*
    @Override
    public List<AlbumResponse> getAlbumsByArtist(String id) {
        List<Album> albums = albumRepository.findByArtistId(id);

        return albums.stream()
                .map(album -> new AlbumResponse(album.getAlbumId(),album.getTitle(), album.getReleaseDate(),
                        album.getCover(), album.getTrackIds(), album.getGenres(),album.getTracks()))
                .collect(Collectors.toList());
    }*/

}
