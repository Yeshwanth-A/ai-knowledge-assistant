package com.yeshwanth.aiknowledgeassistant.service;

import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContextBuilderService {

    public String buildContext(
            List<DocumentChunk> chunks) {

        StringBuilder context = new StringBuilder();

        for (DocumentChunk chunk : chunks) {

            context.append(chunk.getContent());

            context.append("\n\n");
        }

        return context.toString();
    }
}