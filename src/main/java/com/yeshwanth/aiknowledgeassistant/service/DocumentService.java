 package com.yeshwanth.aiknowledgeassistant.service;

import com.yeshwanth.aiknowledgeassistant.entity.Document;
import com.yeshwanth.aiknowledgeassistant.entity.DocumentChunk;
import com.yeshwanth.aiknowledgeassistant.entity.DocumentStatus;
import com.yeshwanth.aiknowledgeassistant.repository.DocumentChunkRepository;
import com.yeshwanth.aiknowledgeassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final PdfTextExtractor pdfTextExtractor;
    private final DocumentChunkRepository documentChunkRepository;
    private final EmbeddingService embeddingService;

    private final Path uploadDirectory = Paths.get("uploads");

    public Document saveDocument(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        Files.createDirectories(uploadDirectory);

        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        Path filePath = uploadDirectory.resolve(fileName);
        file.transferTo(filePath);

        Document document = new Document();
        TextChunker textChunker = new TextChunker();

        document.setFileName(fileName);
        document.setContentType(file.getContentType());
        document.setFileSize(file.getSize());
        document.setStatus(DocumentStatus.UPLOADED);
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);

        String extractedText = pdfTextExtractor.extractText(filePath);

        List<String> chunks = textChunker.chunktText(extractedText);

        for (int i = 0; i < chunks.size(); i++) {

            DocumentChunk documentChunk = new DocumentChunk();

            documentChunk.setDocument(savedDocument);
            documentChunk.setContent(chunks.get(i));
            documentChunk.setChunkIndex(i);

            // Generate embedding using Ollama
            List<Double> embedding =
                    embeddingService.generateEmbedding(chunks.get(i));

            // Convert List<Double> to float[]
            float[] embeddingArray = new float[embedding.size()];

            for (int j = 0; j < embedding.size(); j++) {
                embeddingArray[j] = embedding.get(j).floatValue();
            }

            // Store embedding in DocumentChunk
            documentChunk.setEmbedding(embeddingArray);

            System.out.println(
                    "Chunk " + i +
                            " embedding dimensions: " +
                            embeddingArray.length
            );

            documentChunkRepository.save(documentChunk);
        }

        return savedDocument;
    }
}

