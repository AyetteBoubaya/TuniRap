package com.TuniRap.catalogueManagement.service.serviceImpl;

import com.TuniRap.catalogueManagement.model.Single;
import com.TuniRap.catalogueManagement.repository.SingleRepository;
import com.TuniRap.catalogueManagement.service.SingleService;
import com.TuniRap.catalogueManagement.dto.requests.SingleRequest;
import com.TuniRap.catalogueManagement.dto.responses.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleServiceImpl implements SingleService {

    @Autowired
    private SingleRepository singleRepository;

    @Override
    public SingleResponse addSingle(SingleRequest singleRequest) {
        Single single = new Single();
        single.setSingleId(singleRequest.getSingleId());
        single.setName(singleRequest.getName());
        single.setAlbumId(singleRequest.getAlbumId());
        single.setCover(singleRequest.getCover());
        single.setGenres(singleRequest.getGenres());
        single.setTracks(singleRequest.getTracks());
        single.setScrapped(false);

        Single savedSingle = singleRepository.save(single);

        return new SingleResponse(savedSingle.getName(), savedSingle.getAlbumId(),
                savedSingle.getCover(), savedSingle.getGenres(), savedSingle.getTracks());
    }
} 