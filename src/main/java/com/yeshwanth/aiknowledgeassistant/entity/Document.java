package com.yeshwanth.aiknowledgeassistant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Document {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String contentType;
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
