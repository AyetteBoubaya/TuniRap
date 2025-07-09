package com.TuniRap.catalogueManagement.elasticSearch.controller;

import com.TuniRap.catalogueManagement.elasticSearch.model.AlbumDocument;
import com.TuniRap.catalogueManagement.elasticSearch.service.AlbumSearchService;
import com.TuniRap.catalogueManagement.model.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tuniRap/catalogue/albumSearch")
@RequiredArgsConstructor
public class AlbumSearchController {

    private final AlbumSearchService albumSearchService;

    @PostMapping("/indexAlbum")
    public String indexAlbum(@RequestParam Album album){
        albumSearchService.indexAlbum(album);
        return "Album indexé avec succés";
    }

    @GetMapping("/Title")
    public List<AlbumDocument> searchByTitle(@RequestParam String title){
        return albumSearchService.searchByTitle(title);
    }

    @GetMapping("Title_Genre")
    public List<AlbumDocument> searchByGenreAndTitle(@RequestParam String genre , @RequestParam String title){
        return albumSearchService.searchByGenreAndTitle(genre, title);
    }

    @DeleteMapping("delete/{albumId}")
    public String deleteAlbum(@PathVariable("albumId") String albumId){
        albumSearchService.deleteFromIndex(albumId);
        return "Album supprimé de l'index .";
    }
}
