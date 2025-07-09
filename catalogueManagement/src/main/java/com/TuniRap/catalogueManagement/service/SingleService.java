package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.dto.requests.SingleRequest;
import com.TuniRap.catalogueManagement.dto.responses.SingleResponse;

public interface SingleService {
    SingleResponse addSingle(SingleRequest singleRequest);
    // Ajoutez d'autres méthodes si besoin
} 