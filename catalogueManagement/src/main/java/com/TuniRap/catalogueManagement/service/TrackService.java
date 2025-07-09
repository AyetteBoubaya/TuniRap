package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.dto.requests.TrackRequest;
import com.TuniRap.catalogueManagement.dto.responses.TrackResponse;

import java.util.List;

public interface TrackService {

    TrackResponse addTrack(TrackRequest trackRequest);
    TrackResponse updateTrack(String id, TrackRequest trackRequest);
    void deleteTrack(String id);
    TrackResponse getTrackById(String id);
    List<TrackResponse> getAllTracks();
    List<TrackResponse> getTracksByAlbum(String albumId);
    //List<TrackResponse> getTracksByArtist(String artistId);
    TrackResponse getTrackByTitle(String title);
}
