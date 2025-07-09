package com.TuniRap.catalogueManagement.service.serviceImpl;

import com.TuniRap.catalogueManagement.dto.requests.DiscographieRequest;
import com.TuniRap.catalogueManagement.dto.responses.DiscographieResponse;
import com.TuniRap.catalogueManagement.exceptions.AlreadyExistException;
import com.TuniRap.catalogueManagement.model.Discographie;
import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.model.Track;
import com.TuniRap.catalogueManagement.model.Playlist;
import com.TuniRap.catalogueManagement.repository.DiscographieRepository;
import com.TuniRap.catalogueManagement.repository.AlbumRepository;
import com.TuniRap.catalogueManagement.repository.TrackRepository;
import com.TuniRap.catalogueManagement.repository.PlaylistRepository;
import com.TuniRap.catalogueManagement.service.DiscographieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscographieServiceImpl implements DiscographieService {

    private final DiscographieRepository discographieRepository;
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final PlaylistRepository playlistRepository;

    @Override
    public DiscographieResponse createDiscographie(DiscographieRequest discographieRequest) {
        Discographie discographie = new Discographie();
        discographie.setDiscographieId(discographieRequest.getDiscographieId());
        discographie.setArtistId(discographieRequest.getArtistId());
        discographie.setBio(discographieRequest.getBio());

        Discographie saved = discographieRepository.save(discographie);

        return new DiscographieResponse(saved.getDiscographieId(), saved.getArtistId(), saved.getBio());
    }



    @Override
    public DiscographieResponse updateDiscographie(String id, DiscographieRequest discographieRequest) {
        Discographie discographie = discographieRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistException("Discographie not found with id: " + id));

        discographie.setBio(discographieRequest.getBio());
        Discographie updated = discographieRepository.save(discographie);

        return new DiscographieResponse(updated.getDiscographieId(), updated.getArtistId(), updated.getBio());
    }

    @Override
    public void deleteDiscographie(String id) {
        if (!discographieRepository.existsById(id)) {
            throw new AlreadyExistException("Discographie not found with id: " + id);
        }
        discographieRepository.deleteById(id);
    }

    @Override
    public DiscographieResponse getDiscographieById(String id) {
        Discographie discographie = discographieRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistException("Discographie not found with id: " + id));

        List<Album> albums = albumRepository.findByArtistId(discographie.getArtistId());
        List<Track> tracks = trackRepository.findByArtistId(discographie.getArtistId());
        List<Playlist> playlists = playlistRepository.findByArtistId(discographie.getArtistId());
        List<Track> collaborations = trackRepository.findCollaborationsByArtistId(discographie.getArtistId());

        return new DiscographieResponse(
                discographie.getDiscographieId(),
                discographie.getArtistId(),
                discographie.getBio(),
                albums,
                tracks,
                playlists,
                collaborations
        );
    }

    @Override
    public List<DiscographieResponse> getAllDiscographies() {
        List<Discographie> discographies = discographieRepository.findAll();

        return discographies.stream().map(discographie -> {
            List<Album> albums = albumRepository.findByArtistId(discographie.getArtistId());
            List<Track> tracks = trackRepository.findByArtistId(discographie.getArtistId());
            List<Playlist> playlists = playlistRepository.findByArtistId(discographie.getArtistId());
            List<Track> collaborations = trackRepository.findCollaborationsByArtistId(discographie.getArtistId());

            return new DiscographieResponse(
                    discographie.getDiscographieId(),
                    discographie.getArtistId(),
                    discographie.getBio(),
                    albums,
                    tracks,
                    playlists,
                    collaborations
            );
        }).toList();
    }
}
