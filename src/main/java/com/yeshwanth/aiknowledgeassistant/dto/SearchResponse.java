package com.yeshwanth.aiknowledgeassistant.dto;

public record SearchResponse(
        Long chunkId,
        Integer chunkIndex,
        String content
) {
}