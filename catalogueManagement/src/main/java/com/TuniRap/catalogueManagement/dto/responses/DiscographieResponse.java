package com.TuniRap.catalogueManagement.dto.responses;

import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.model.Track;
import com.TuniRap.catalogueManagement.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscographieResponse {

    private String discographieId;
    private String artistId;
    private String bio;

    private List<Album> albums;
    private List<Track> tracks;
    private List<Playlist> playlists;
    private List<Track> collaborations;

    // 👉 Constructeur 3 arguments pour create et update
    public DiscographieResponse(String discographieId, String artistId, String bio) {
        this.discographieId = discographieId;
        this.artistId = artistId;
        this.bio = bio;
    }
}
