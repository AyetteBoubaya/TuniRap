package com.TuniRap.catalogueManagement.controller;

import com.TuniRap.catalogueManagement.dto.UserInfoDTO;
import com.TuniRap.catalogueManagement.dto.requests.PlaylistRequest;
import com.TuniRap.catalogueManagement.dto.responses.PlaylistResponse;
import com.TuniRap.catalogueManagement.service.PlaylistService;
import com.TuniRap.catalogueManagement.service.AuthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tuniRap/catalogue/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final AuthServiceClient authServiceClient;

    public PlaylistController(PlaylistService playlistService, AuthServiceClient authServiceClient) {
        this.playlistService = playlistService;
        this.authServiceClient = authServiceClient;
    }

    @PostMapping
    public ResponseEntity<?> addPlaylist(@RequestHeader("Authorization") String token, @RequestBody PlaylistRequest playlistRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("FAN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les fans peuvent créer une playlist.");
        }
        PlaylistResponse response = playlistService.addPlaylist(playlistRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody PlaylistRequest playlistRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("FAN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les fans peuvent modifier leur playlist.");
        }
        PlaylistResponse response = playlistService.updatePlaylist(id, playlistRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("FAN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les fans peuvent supprimer leur playlist.");
        }
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        // autorisation FAN, ARTISTE, ORGANISATEUR, ADMIN pour lecture
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        PlaylistResponse response = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllPlaylists(@RequestHeader("Authorization") String token) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        List<PlaylistResponse> responses = playlistService.getAllPlaylists();
        return ResponseEntity.ok(responses);
    }

    /*
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<PlaylistResponse>> getPlaylistsByArtist(@PathVariable String artistId) {
        List<PlaylistResponse> responses = playlistService.getPlaylistsByArtist(artistId);
        return ResponseEntity.ok(responses);
    }
    */
}
