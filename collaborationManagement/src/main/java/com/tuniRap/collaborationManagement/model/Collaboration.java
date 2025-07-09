package com.tuniRap.collaborationManagement.model;


import com.tuniRap.collaborationManagement.model.enums.Status;
import com.tuniRap.collaborationManagement.model.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="collaboration")
@Data
public class Collaboration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId; // organisateur ou artiste
    private Long receiverId; //

    @Enumerated(EnumType.STRING)
    private Type collaborationType;

    @Enumerated(EnumType.STRING)
    private Status collaborationStatus;

    private String subject;
    private String description;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "collaboration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;
}
