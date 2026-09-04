package com.yeshwanth.aiknowledgeassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public EmbeddingService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();

        this.objectMapper = new ObjectMapper();
    }

    public List<Double> generateEmbedding(String text) {

        try {
            var request = objectMapper.createObjectNode();

            request.put("model", "nomic-embed-text");
            request.put("input", text);

            String response = restClient.post()
                    .uri("/api/embed")
                    .body(request.toString())
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);

            JsonNode embeddingArray = root
                    .get("embeddings")
                    .get(0);

            List<Double> embedding = new ArrayList<>();

            for (JsonNode value : embeddingArray) {
                embedding.add(value.asDouble());
            }

            return embedding;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate embedding", e);
        }
    }
}