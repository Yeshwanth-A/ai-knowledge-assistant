package com.yeshwanth.aiknowledgeassistant.controller;

import com.yeshwanth.aiknowledgeassistant.entity.Document;
import com.yeshwanth.aiknowledgeassistant.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public Document uploadDocuments(@RequestParam("file") MultipartFile file) throws IOException {

        return documentService.saveDocument(file);
    }
}
