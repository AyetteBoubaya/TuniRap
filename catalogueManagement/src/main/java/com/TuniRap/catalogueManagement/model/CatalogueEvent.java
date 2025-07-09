package com.TuniRap.catalogueManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogueEvent {

    @JsonProperty("id")
    private String id;

    @NotBlank(message = "eventType ne peut pas être vide ou null")
    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("entityId")
    private String entityId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    public CatalogueEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public CatalogueEvent(String eventType, String entityId, String message) {
        this.eventType = eventType;
        this.entityId = entityId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters et setters

    public void setId(String id) {
        this.id = id;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CatalogueEvent{" +
                "id='" + id + '\'' +
                ", eventType='" + eventType + '\'' +
                ", entityId='" + entityId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
