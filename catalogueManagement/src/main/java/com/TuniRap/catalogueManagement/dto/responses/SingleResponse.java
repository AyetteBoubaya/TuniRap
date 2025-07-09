package com.TuniRap.catalogueManagement.dto.responses;

import com.TuniRap.catalogueManagement.model.Category;
import com.TuniRap.catalogueManagement.model.Track;

import java.util.List;

public record SingleResponse(String name, String AlbumId, String cover, List<Category> genres, List<Track> tracks) {
}
