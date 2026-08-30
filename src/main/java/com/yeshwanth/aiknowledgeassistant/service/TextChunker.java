package com.yeshwanth.aiknowledgeassistant.service;

import java.util.ArrayList;
import java.util.List;

public class TextChunker {

    private final int chunkSize = 1000;
    private final int overlap = 200;

    public List<String> chunktText(String text) {

        List<String> chunks = new ArrayList<>();

        int start = 0;

        while (start < text.length()) {

            int end = Math.min(start + chunkSize, text.length());

            String chunk = text.substring(start, end);

            chunks.add(chunk);

            if (end == text.length()) {
                break;
            }

            start = end - overlap;
        }
        return chunks;
    }

}

