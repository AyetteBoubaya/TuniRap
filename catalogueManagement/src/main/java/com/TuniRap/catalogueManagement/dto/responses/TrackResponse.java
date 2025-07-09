package com.TuniRap.catalogueManagement.dto.responses;

import java.util.Date;
import java.util.List;

public record TrackResponse(String trackId , String title ,
                            int popularity , int duration ,
                            boolean Explicit ,String previewURL ,
                            int TrackNumber,List<String> featurings ,
                            String Cover , Date releaseDate) {
}
