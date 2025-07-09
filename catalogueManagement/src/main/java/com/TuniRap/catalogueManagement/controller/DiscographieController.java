package com.TuniRap.catalogueManagement.controller;

import com.TuniRap.catalogueManagement.dto.UserInfoDTO;
import com.TuniRap.catalogueManagement.dto.requests.DiscographieRequest;
import com.TuniRap.catalogueManagement.dto.responses.DiscographieResponse;
import com.TuniRap.catalogueManagement.service.AuthServiceClient;
import com.TuniRap.catalogueManagement.service.DiscographieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tuniRap/catalogue/discographie")
public class DiscographieController {

    private final DiscographieService discographieService;
    private final AuthServiceClient authServiceClient;

    public DiscographieController(DiscographieService discographieService, AuthServiceClient authServiceClient) {
        this.discographieService = discographieService;
        this.authServiceClient = authServiceClient;
    }

    @PostMapping
    public ResponseEntity<?> createDiscographie(@RequestHeader("Authorization") String token, @RequestBody DiscographieRequest discographieRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent créer une discographie.");
        }
        DiscographieResponse response = discographieService.createDiscographie(discographieRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiscographie(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody DiscographieRequest discographieRequest) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent modifier une discographie.");
        }
        DiscographieResponse response = discographieService.updateDiscographie(id, discographieRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscographie(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!user.role().equals("ARTISTE")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seuls les artistes peuvent supprimer une discographie.");
        }
        discographieService.deleteDiscographie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiscographieById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        DiscographieResponse response = discographieService.getDiscographieById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllDiscographies(@RequestHeader("Authorization") String token) {
        UserInfoDTO user = authServiceClient.validateToken(token);
        if (!AuthServiceClient.hasRole(user, "FAN", "ARTISTE", "ORGANISATEUR", "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accès non autorisé.");
        }
        List<DiscographieResponse> responses = discographieService.getAllDiscographies();
        return ResponseEntity.ok(responses);
    }

    /*
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<DiscographieResponse> getDiscographieByArtist(@PathVariable String artistId) {
        DiscographieResponse response = discographieService.getDiscographiesByArtist(artistId);
        return ResponseEntity.ok(response);
    }
    */
}
