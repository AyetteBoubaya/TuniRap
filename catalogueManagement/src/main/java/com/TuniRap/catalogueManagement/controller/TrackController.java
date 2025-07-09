package com.TuniRap.catalogueManagement.controller;

import com.TuniRap.catalogueManagement.dto.UserInfoDTO;
import com.TuniRap.catalogueManagement.dto.requests.TrackRequest;
import com.TuniRap.catalogueManagement.dto.responses.TrackResponse;
import com.TuniRap.catalogueManagement.service.AuthServiceClient;
import com.TuniRap.catalogueManagement.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tuniRap/catalogue/track")
public class TrackController {

    private final TrackService trackService;
    private final AuthServiceClient authServiceClient;

    public TrackController(TrackService trackService, AuthServiceClient authServiceClient) {
        this.trackService = trackService;
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("addTrack")
    public ResponseEntity<?> addTrack(@RequestHeader("Authorization") String token, @RequestBody TrackRequest trackRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent ajouter un track.");
        }
        TrackResponse trackResponse = trackService.addTrack(trackRequest);
        return ResponseEntity.ok(trackResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrack(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody TrackRequest trackRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent modifier un track.");
        }
        TrackResponse response = trackService.updateTrack(id, trackRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent supprimer un track.");
        }
        trackService.deleteTrack(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrackById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        TrackResponse response = trackService.getTrackById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllTracks(@RequestHeader("Authorization") String token) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        List<TrackResponse> responses = trackService.getAllTracks();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<?> getTracksByAlbum(@RequestHeader("Authorization") String token, @PathVariable String albumId) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        List<TrackResponse> responses = trackService.getTracksByAlbum(albumId);
        return ResponseEntity.ok(responses);
    }

    /*
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<TrackResponse>> getTracksByArtist(@PathVariable String artistId) {
        List<TrackResponse> responses = trackService.getTracksByArtist(artistId);
        return ResponseEntity.ok(responses);
    }
    */

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getTrackByTitle(@RequestHeader("Authorization") String token, @PathVariable String title) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        TrackResponse response = trackService.getTrackByTitle(title);
        return ResponseEntity.ok(response);
    }
}
