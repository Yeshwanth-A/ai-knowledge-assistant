package com.yeshwanth.aiknowledgeassistant.controller;

import com.yeshwanth.aiknowledgeassistant.dto.SearchRequest;
import com.yeshwanth.aiknowledgeassistant.dto.SearchResponse;
import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import com.yeshwanth.aiknowledgeassistant.service.RetrievalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final RetrievalService retrievalService;

    @PostMapping
    public List<SearchResponse> search(
            @Valid @RequestBody SearchRequest request) {

        List<DocumentChunk> chunks =
                retrievalService.retrieveRelevantChunks(
                        request.documentId(),
                        request.question(),
                        5
                );

        return chunks.stream()
                .map(chunk -> new SearchResponse(
                        chunk.getId(),
                        chunk.getChunkIndex(),
                        chunk.getContent()
                ))
                .toList();
    }
}