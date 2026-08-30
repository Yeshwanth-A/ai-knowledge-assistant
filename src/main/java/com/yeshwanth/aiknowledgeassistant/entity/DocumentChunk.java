package com.yeshwanth.aiknowledgeassistant.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Integer chunkIndex;

    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;


}
