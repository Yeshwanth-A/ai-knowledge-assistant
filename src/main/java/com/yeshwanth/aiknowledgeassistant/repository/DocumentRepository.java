package com.yeshwanth.aiknowledgeassistant.repository;

import com.yeshwanth.aiknowledgeassistant.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}