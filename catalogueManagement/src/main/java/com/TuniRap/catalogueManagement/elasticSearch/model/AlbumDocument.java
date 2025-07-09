package com.TuniRap.catalogueManagement.elasticSearch.model;

import com.TuniRap.catalogueManagement.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "albums")
@Builder
public class AlbumDocument {

    @Id
    private String albumId;
    private String title;
    private Date releaseDate;
    private String cover; // url
    private List<Category> genres;
}
