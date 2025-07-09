package com.TuniRap.catalogueManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "discographies")
@Data
public class Discographie {

    @Id
    private String discographieId;
    private String artistId; // clé de référence vers l'artiste (ou User si tu préfères)
    private String bio; // biographie de l’artiste
}
