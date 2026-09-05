package com.yeshwanth.aiknowledgeassistant.service;

import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import com.yeshwanth.aiknowledgeassistant.repository.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository documentChunkRepository;

    public List<DocumentChunk> retrieveRelevantChunks(
            Long documentId,
            String question,
            int limit) {

        if (documentId == null) {
            throw new IllegalArgumentException(
                    "Document ID cannot be null"
            );
        }

        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException(
                    "Question cannot be empty"
            );
        }

        // Question → 768-dimensional embedding
        List<Double> embedding =
                embeddingService.generateEmbedding(question);

        if (embedding.size() != 768) {
            throw new IllegalStateException(
                    "Expected 768-dimensional embedding but got "
                            + embedding.size()
            );
        }

        // Java List → pgvector format
        String vector = embedding.toString();

        // Search only inside the selected document
        return documentChunkRepository.findSimilarChunks(
                documentId,
                vector,
                limit
        );
    }
}