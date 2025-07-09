package com.TuniRap.catalogueManagement.dto.requests;

import com.TuniRap.catalogueManagement.model.Category;
import com.TuniRap.catalogueManagement.model.Track;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AlbumRequest {
    private String AlbumId;
    private String Title;
    private Date releaseDate;
    private String Cover; // url
    private List<String> trackIds; // Liste des IDs des morceaux
    private List<Category> genres;
    private List<Track>  tracks;
}
