package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.dto.requests.DiscographieRequest;
import com.TuniRap.catalogueManagement.dto.requests.TrackRequest;
import com.TuniRap.catalogueManagement.dto.responses.DiscographieResponse;
import com.TuniRap.catalogueManagement.dto.responses.TrackResponse;

import java.util.List;

public interface DiscographieService {
    DiscographieResponse createDiscographie(DiscographieRequest discographieRequest);
    DiscographieResponse updateDiscographie(String id, DiscographieRequest discographieRequest);
    void deleteDiscographie(String id);
    DiscographieResponse getDiscographieById(String id);
    List<DiscographieResponse> getAllDiscographies();
    //DiscographieResponse getDiscographiesByArtist(String artistId);
}
