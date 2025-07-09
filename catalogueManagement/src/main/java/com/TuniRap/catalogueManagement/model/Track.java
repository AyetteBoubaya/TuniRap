package com.TuniRap.catalogueManagement.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@Document(collection = "tracks")
public class Track {
    @Id
    private String id;

    private String trackId;
    private String title;
    private int popularity;
    private int duration;
    private boolean Explicit;
    private String previewURL;
    private int TrackNumber;
    private List<String> featurings;
    private String Cover; //url
    private Date releaseDate;
    private String AlbumId;
    private Long artistId;
    private Boolean scrapped;
    private LocalDateTime  scrappedAt;
    //singleId
}
