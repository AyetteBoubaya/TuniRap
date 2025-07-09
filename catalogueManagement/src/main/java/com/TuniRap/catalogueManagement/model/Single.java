package com.TuniRap.catalogueManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "singles")
public class Single {

    @Id
    private String id;

    private String singleId;
    private String name;
    private String AlbumId;
    private String cover;//url
    private List<Category> genres;
    private List<Track> tracks;
    private Boolean scrapped;
    private LocalDateTime scrappedAt;

}
