package com.TuniRap.catalogueManagement.dto.requests;

import com.TuniRap.catalogueManagement.model.Category;
import com.TuniRap.catalogueManagement.model.Track;
import lombok.Data;

import java.util.List;

@Data
public class SingleRequest {
    private String singleId;
    private String name;
    private String AlbumId;
    private String cover;
    private List<Category> genres;
    private List<Track> tracks;
}
