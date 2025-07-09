package com.TuniRap.catalogueManagement.dto.responses;

import com.TuniRap.catalogueManagement.model.Category;
import com.TuniRap.catalogueManagement.model.Track;

import java.util.Date;
import java.util.List;

public record AlbumResponse(String AlbumId,String Title,Date releaseDate,String Cover
        ,List<String>trackIds
        ,List<Category> genres,List<Track> tracks) {
}
