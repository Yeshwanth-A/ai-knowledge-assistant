package com.yeshwanth.aiknowledgeassistant.service;

import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskService {

    private final RetrievalService retrievalService;
    private final ContextBuilderService contextBuilderService;
    private final LlmService llmService;

    public String ask(
            Long documentId,
            String question) {

        List<DocumentChunk> chunks =
                retrievalService.retrieveRelevantChunks(
                        documentId,
                        question,
                        5
                );


        String context = contextBuilderService.buildContext(chunks);

        return llmService.generateAnswer(
                question,
                context
        );
    }
}