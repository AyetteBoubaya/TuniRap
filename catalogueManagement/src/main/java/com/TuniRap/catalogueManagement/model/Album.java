package com.TuniRap.catalogueManagement.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "albums")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    private String Id;

    private String albumId;
    private String Title;
    private Date releaseDate;
    private String Cover; // url
    private List<String> trackIds; // Liste des IDs des morceaux
    private List<Category> genres;
    private List<Track>  tracks;
    private String artistId;
    private Boolean scrapped;
    private LocalDateTime scrappedAt;
}
