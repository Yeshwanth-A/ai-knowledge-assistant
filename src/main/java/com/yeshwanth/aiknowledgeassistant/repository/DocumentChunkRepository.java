package com.yeshwanth.aiknowledgeassistant.repository;

import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentChunkRepository
        extends JpaRepository<DocumentChunk, Long> {
}