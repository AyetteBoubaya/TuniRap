package com.tuniRap.notifications.model;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String recipientId;
    private String title;
    private String content;
    private String type;
    private String status;
    private Date createdAt;
    private Date readAt;
    private String relatedObjectId;
    
}
