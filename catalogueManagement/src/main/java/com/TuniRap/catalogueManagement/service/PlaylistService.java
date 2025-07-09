package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.dto.requests.PlaylistRequest;
import com.TuniRap.catalogueManagement.dto.requests.TrackRequest;
import com.TuniRap.catalogueManagement.dto.responses.PlaylistResponse;
import com.TuniRap.catalogueManagement.dto.responses.TrackResponse;

import java.util.List;

public interface PlaylistService {
    PlaylistResponse addPlaylist(PlaylistRequest playlistRequest);
    PlaylistResponse updatePlaylist(String id, PlaylistRequest playlistRequest);
    void deletePlaylist(String id);
    PlaylistResponse getPlaylistById(String id);
    List<PlaylistResponse> getAllPlaylists();
    //List<PlaylistResponse> getPlaylistsByArtist(String artistId);

}
