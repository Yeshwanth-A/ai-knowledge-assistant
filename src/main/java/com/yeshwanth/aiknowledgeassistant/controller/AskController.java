package com.yeshwanth.aiknowledgeassistant.controller;

import com.yeshwanth.aiknowledgeassistant.dto.AskRequest;
import com.yeshwanth.aiknowledgeassistant.dto.AskResponse;
import com.yeshwanth.aiknowledgeassistant.service.AskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ask")
public class AskController {

    private final AskService askService;

    @PostMapping
    public AskResponse ask(
            @Valid @RequestBody AskRequest request) {

        String answer =
                askService.ask(
                        request.documentId(),
                        request.question()
                );

        return new AskResponse(answer);
    }
}