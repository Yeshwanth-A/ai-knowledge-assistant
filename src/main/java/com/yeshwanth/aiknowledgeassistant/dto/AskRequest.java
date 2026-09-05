package com.yeshwanth.aiknowledgeassistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AskRequest(

        @NotNull(message = "Document ID cannot be null")
        Long documentId,

        @NotBlank(message = "Question cannot be empty")
        String question

) {
}