package com.TuniRap.catalogueManagement.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "playlists")
@Data
public class Playlist {

    @Id
    private  String id;

    private  String playlistId;
    private  String name;
    private Long artistId;
    private LocalDate scrapedDate;
    //relation avec artists
}
