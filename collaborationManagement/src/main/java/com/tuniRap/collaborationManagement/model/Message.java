package com.tuniRap.collaborationManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long senderId;

    private String content;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "collaboration_id")
    private Collaboration collaboration;


}
