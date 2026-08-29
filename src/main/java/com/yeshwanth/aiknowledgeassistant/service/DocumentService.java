package com.yeshwanth.aiknowledgeassistant.service;

import com.yeshwanth.aiknowledgeassistant.entity.Document;
import com.yeshwanth.aiknowledgeassistant.entity.DocumentStatus;
import com.yeshwanth.aiknowledgeassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final PdfTextExtractor pdfTextExtractor;

    private final Path uploadDirectory = Paths.get("uploads");

    public Document saveDocument(MultipartFile file) throws IOException {

        Files.createDirectories(uploadDirectory);

        String fileName = file.getOriginalFilename();

        Path filePath = null;

        if (fileName != null) {
            filePath = uploadDirectory.resolve(fileName);
        }


        if (filePath != null) {
            file.transferTo(filePath);
        }

        String extractedText = pdfTextExtractor.extractText(filePath);
        System.out.println(extractedText);

        Document document = new Document();

        document.setFileName(fileName);
        document.setContentType(file.getContentType());
        document.setFileSize(file.getSize());
        document.setStatus(DocumentStatus.UPLOADED);
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        return documentRepository.save(document);
    }
}