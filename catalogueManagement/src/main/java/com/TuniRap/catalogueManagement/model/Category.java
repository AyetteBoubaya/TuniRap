package com.TuniRap.catalogueManagement.model;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public enum Category {

    RAP ,
    ROCK,
    HIPHOP,
    CLASSICAL,
    moroccanrap,
    raï,
    moroccanpo,
    OTHER
}
