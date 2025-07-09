package com.TuniRap.catalogueManagement.service.serviceImpl;

import com.TuniRap.catalogueManagement.dto.requests.PlaylistRequest;
import com.TuniRap.catalogueManagement.dto.responses.PlaylistResponse;
import com.TuniRap.catalogueManagement.exceptions.AlreadyExistException;
import com.TuniRap.catalogueManagement.model.Playlist;
import com.TuniRap.catalogueManagement.repository.PlaylistRepository;
import com.TuniRap.catalogueManagement.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public PlaylistResponse addPlaylist(PlaylistRequest playlistRequest) {
        Playlist playlist = new Playlist();

        playlist.setName(playlistRequest.getName());
        playlist.setName(playlistRequest.getName());
        Playlist savedplaylist = playlistRepository.save(playlist);

        return new PlaylistResponse(savedplaylist.getPlaylistId(), savedplaylist.getName());
    }

    @Override
    public PlaylistResponse updatePlaylist(String id, PlaylistRequest playlistRequest) {

        Optional<Playlist> playlistOpt = playlistRepository.findById(id);

        if (!playlistOpt.isPresent()) {
            throw new AlreadyExistException("Playlist  not found with id: " + id);
        }
        Playlist playlist = playlistOpt.get();
        playlist.setPlaylistId(playlistRequest.getPlaylistId());
        playlist.setName(playlistRequest.getName());
        Playlist savedPlaylist = playlistRepository.save(playlistOpt.get());

        return new PlaylistResponse(savedPlaylist.getPlaylistId(), savedPlaylist.getName());
    }

    @Override
    public void deletePlaylist(String id) {
        if (!playlistRepository.existsById(id)) {
            throw new AlreadyExistException("Playlist not found with id: " + id);
        }
        playlistRepository.deleteById(id);
    }

    @Override
    public PlaylistResponse getPlaylistById(String id) {
        Optional<Playlist> playlistOpt = playlistRepository.findById(id);

        if (!playlistOpt.isPresent()) {
            throw new AlreadyExistException("Playlist  not found with id: " + id);
        }
        Playlist playlist = playlistOpt.get();
        return new PlaylistResponse(playlist.getPlaylistId(), playlist.getName());
    }

    @Override
    public List<PlaylistResponse> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();

        return  playlists.stream()
                .map(playlist -> new PlaylistResponse(
                        playlist.getPlaylistId(), playlist.getName()))
                .collect(Collectors.toList());
    }
    /*
    @Override
    public List<PlaylistResponse> getPlaylistsByArtist(String artistId) {
        List<Playlist> playlists = playlistRepository.findByArtistId(artistId);

        return playlists.stream()
                .map(playlist -> new PlaylistResponse(
                        playlist.getPlaylistId(), playlist.getName()))
                .collect(Collectors.toList());
    }*/


}
