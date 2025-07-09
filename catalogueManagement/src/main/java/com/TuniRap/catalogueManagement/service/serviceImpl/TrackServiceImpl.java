package com.TuniRap.catalogueManagement.service.serviceImpl;


import com.TuniRap.catalogueManagement.dto.requests.TrackRequest;
import com.TuniRap.catalogueManagement.dto.responses.TrackResponse;
import com.TuniRap.catalogueManagement.exceptions.AlreadyExistException;
import com.TuniRap.catalogueManagement.model.Track;
import com.TuniRap.catalogueManagement.repository.TrackRepository;
import com.TuniRap.catalogueManagement.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Override
    public TrackResponse addTrack(TrackRequest trackRequest) {

        Track track = new Track();
        track.setTrackId(trackRequest.getTrackId());
        track.setTitle(trackRequest.getTitle());
        track.setPopularity(trackRequest.getPopularity());
        track.setDuration(trackRequest.getDuration());
        track.setExplicit(trackRequest.isExplicit());
        track.setPreviewURL(trackRequest.getPreviewURL());
        track.setTrackNumber(trackRequest.getTrackNumber());
        track.setFeaturings(trackRequest.getFeaturings());
        track.setCover(trackRequest.getCover());
        track.setReleaseDate(trackRequest.getReleaseDate());
        track.setScrapped(false);

        Track savedTrack = trackRepository.save(track);

        return new TrackResponse(savedTrack.getTrackId(), savedTrack.getTitle(),
                savedTrack.getPopularity() , savedTrack.getDuration(),
                savedTrack.isExplicit(), savedTrack.getPreviewURL(),
                savedTrack.getTrackNumber(),savedTrack.getFeaturings() ,
                savedTrack.getCover(), savedTrack.getReleaseDate());

    }

    @Override
    public TrackResponse updateTrack(String id, TrackRequest trackRequest) {
        Optional<Track> trackOpt = trackRepository.findById(id);
        if (!trackOpt.isPresent()) {
            throw new AlreadyExistException("Album not found with id: " + id);
        }

        Track track = new Track();
        track.setTrackId(trackRequest.getTrackId());
        track.setTitle(trackRequest.getTitle());
        track.setPopularity(trackRequest.getPopularity());
        track.setDuration(trackRequest.getDuration());
        track.setExplicit(trackRequest.isExplicit());
        track.setPreviewURL(trackRequest.getPreviewURL());
        track.setTrackNumber(trackRequest.getTrackNumber());
        track.setFeaturings(trackRequest.getFeaturings());
        track.setCover(track.getCover()); //url
        track.setReleaseDate(trackRequest.getReleaseDate());

        Track updatededTrack = trackRepository.save(track);

        return new TrackResponse(updatededTrack.getTrackId(), updatededTrack.getTitle(),
                updatededTrack.getPopularity() , updatededTrack.getDuration(),
                updatededTrack.isExplicit(), updatededTrack.getPreviewURL(),
                updatededTrack.getTrackNumber(),updatededTrack.getFeaturings() ,
                updatededTrack.getCover(), updatededTrack.getReleaseDate());
    }

    @Override
    public void deleteTrack(String id) {
        if (!trackRepository.existsById(id)) {
            throw new AlreadyExistException("Track not found with id: " + id);
        }

        trackRepository.deleteById(id);
    }

    @Override
    public TrackResponse getTrackById(String id) {
        Optional<Track> trackOpt = trackRepository.findById(id);
        if (!trackOpt.isPresent()) {
            throw new AlreadyExistException("Track not found with id: " + id);
        }

        Track track = trackOpt.get();
        return new TrackResponse(track.getTrackId(), track.getTitle(),
                track.getPopularity() , track.getDuration(),
                track.isExplicit(), track.getPreviewURL(),
                track.getTrackNumber(),track.getFeaturings() ,
                track.getCover(), track.getReleaseDate());

    }

    @Override
    public List<TrackResponse> getAllTracks() {
        List<Track> tracks = trackRepository.findAll();

        return tracks.stream()
                .map(track -> new TrackResponse(track.getTrackId(), track.getTitle(),
                        track.getPopularity() , track.getDuration(),
                        track.isExplicit(), track.getPreviewURL(),
                        track.getTrackNumber(),track.getFeaturings() ,
                        track.getCover(), track.getReleaseDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackResponse> getTracksByAlbum(String albumId) {
        List<Track> tracks = trackRepository.findByAlbumId(albumId);

        return tracks.stream()
                .map(track -> new TrackResponse(track.getTrackId(), track.getTitle(),
                        track.getPopularity() , track.getDuration(),
                        track.isExplicit(), track.getPreviewURL(),
                        track.getTrackNumber(),track.getFeaturings() ,
                        track.getCover(), track.getReleaseDate()))
                .collect(Collectors.toList());
    }

    /*
    @Override
    public List<TrackResponse> getTracksByArtist(String artistId) {
        List<Track> tracks = trackRepository.findByArtistId(artistId);

        return tracks.stream()
                .map(track -> new TrackResponse(track.getTrackId(), track.getTitle(),
                        track.getPopularity() , track.getDuration(),
                        track.isExplicit(), track.getPreviewURL(),
                        track.getTrackNumber(),track.getFeaturings() ,
                        track.getCover(), track.getReleaseDate()))
                .collect(Collectors.toList());
    }*/

    @Override
    public TrackResponse getTrackByTitle(String title) {
        Optional<Track> trackOpt = Optional.ofNullable(trackRepository.findByTitle(title));

        if (trackOpt.isEmpty()) {
            throw new AlreadyExistException("Track not found with title: " + title);
        }

        Track track = trackOpt.get();
        return new TrackResponse(track.getTrackId(), track.getTitle(),
                track.getPopularity() , track.getDuration(),
                track.isExplicit(), track.getPreviewURL(),
                track.getTrackNumber(),track.getFeaturings() ,
                track.getCover(), track.getReleaseDate());
    }

}
