package com.yeshwanth.aiknowledgeassistant.repository;

import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentChunkRepository
        extends JpaRepository<DocumentChunk, Long> {

    @Query(value = """
            SELECT *
            FROM document_chunk
            WHERE document_id = :documentId
            ORDER BY embedding <=> CAST(:embedding AS vector)
            LIMIT :limit
            """, nativeQuery = true)
    List<DocumentChunk> findSimilarChunks(
            @Param("documentId") Long documentId,
            @Param("embedding") String embedding,
            @Param("limit") int limit
    );
}