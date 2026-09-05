package com.yeshwanth.aiknowledgeassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LlmService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public LlmService() {

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();

        this.objectMapper = new ObjectMapper();
    }

    public String generateAnswer(
            String question,
            String context) {

        try {

            String prompt = """
                    You are a helpful AI assistant.

                    Answer the user's question using ONLY the
                    information provided in the context.

                    If the answer cannot be found in the context,
                    say: "I could not find the answer in the document."

                    Do not make up information.

                    Context:
                    %s

                    Question:
                    %s

                    Answer:
                    """.formatted(context, question);

            var request = objectMapper.createObjectNode();

            request.put("model", "llama3.2");

            request.put("prompt", prompt);

            request.put("stream", false);

            String response = restClient.post()
                    .uri("/api/generate")
                    .body(request.toString())
                    .retrieve()
                    .body(String.class);

            JsonNode root =
                    objectMapper.readTree(response);

            return root.get("response").asText();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate answer using Ollama",
                    e
            );
        }
    }
}