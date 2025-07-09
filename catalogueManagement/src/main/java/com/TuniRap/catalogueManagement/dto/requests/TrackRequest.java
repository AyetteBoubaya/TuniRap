package com.TuniRap.catalogueManagement.dto.requests;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrackRequest {

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
}
