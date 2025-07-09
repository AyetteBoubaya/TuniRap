package com.TuniRap.catalogueManagement.dto.requests;

import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.model.Playlist;
import com.TuniRap.catalogueManagement.model.Track;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data

public class DiscographieRequest {

    private String discographieId;
    private String artistId;
    private String bio;
}
