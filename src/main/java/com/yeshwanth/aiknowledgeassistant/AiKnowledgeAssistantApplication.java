package com.yeshwanth.aiknowledgeassistant;

import com.yeshwanth.aiknowledgeassistant.service.EmbeddingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiKnowledgeAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiKnowledgeAssistantApplication.class, args);
    }

    @Bean
    CommandLineRunner testEmbedding(EmbeddingService embeddingService) {
        return args -> {

            var embedding = embeddingService.generateEmbedding(
                    "Spring Boot uses dependency injection."
            );

            System.out.println("Dimensions: " + embedding.size());
            System.out.println("First value: " + embedding.get(0));
        };
    }
}