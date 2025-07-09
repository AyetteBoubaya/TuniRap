package com.TuniRap.catalogueManagement.controller;

import com.TuniRap.catalogueManagement.dto.UserInfoDTO;
import com.TuniRap.catalogueManagement.dto.requests.AlbumRequest;
import com.TuniRap.catalogueManagement.dto.responses.AlbumResponse;
import com.TuniRap.catalogueManagement.service.AlbumService;
import com.TuniRap.catalogueManagement.service.AuthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tuniRap/catalogue/album")
public class AlbumController {

    private final AlbumService albumService;
    private final AuthServiceClient authServiceClient;

    public AlbumController(AlbumService albumService, AuthServiceClient authServiceClient) {
        this.albumService = albumService;
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(@RequestHeader("Authorization") String token, @RequestBody AlbumRequest albumRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent créer un album.");
        }
        albumService.addAlbum(albumRequest);
        return ResponseEntity.ok("Album créé avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody AlbumRequest albumRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent mettre à jour un album.");
        }
        albumService.updateAlbum(id, albumRequest);
        return ResponseEntity.ok("Album mis à jour avec succès.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent supprimer un album.");
        }
        albumService.deleteAlbum(id);
        return ResponseEntity.ok("Album supprimé avec succès.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbumById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "ARTISTE", "FAN", "ADMIN", "ORGANIZATEUR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé à cet album.");
        }
        AlbumResponse album = albumService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping
    public ResponseEntity<?> getAllAlbums(@RequestHeader("Authorization") String token) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "ARTISTE", "FAN", "ADMIN", "ORGANIZATEUR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé aux albums.");
        }
        List<AlbumResponse> responses = albumService.getAllAlbums();
        return ResponseEntity.ok(responses);
    }

    /*
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<AlbumResponse>> getAlbumsByArtist(@PathVariable String artistId) {
        List<AlbumResponse> responses = albumService.getAlbumsByArtist(artistId);
        return ResponseEntity.ok(responses);
    }
    */
}
