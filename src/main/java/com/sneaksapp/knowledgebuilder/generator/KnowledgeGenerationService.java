package com.sneaksapp.knowledgebuilder.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneaksapp.knowledgebuilder.model.Knowledge;
import com.sneaksapp.knowledgebuilder.model.OutfitCategoryPlan;
import com.sneaksapp.knowledgebuilder.model.OutfitSelection;
import com.sneaksapp.knowledgebuilder.openai.*;
import com.sneaksapp.knowledgebuilder.openai.response.*;
import com.sneaksapp.knowledgebuilder.parser.KnowledgeParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KnowledgeGenerationService {

    private static final String VISION_PROMPT = "vision-analysis.txt";

    private final PromptBuilder promptBuilder;
    private final OpenAiService openAiService;
    private final KnowledgeParser knowledgeParser;

    public KnowledgeGenerationService(
            PromptBuilder promptBuilder,
            OpenAiService openAiService,
            KnowledgeParser knowledgeParser) {

        this.promptBuilder = promptBuilder;
        this.openAiService = openAiService;
        this.knowledgeParser = knowledgeParser;
    }

    public Knowledge generate(String imageUrl) {

        String prompt = promptBuilder.loadPrompt(VISION_PROMPT);

        OpenAiResponse response = openAiService.sendPrompt(
                prompt,
                imageUrl
        );

        return knowledgeParser.parse(response);
    }

    public List<OutfitCategoryPlan> selectOutfitCategories(String prompt) {
        OpenAiResponse response = openAiService.sendPrompt(prompt, null);
        String content = extractOutputText(response);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(
                    content.trim(),
                    new TypeReference<List<OutfitCategoryPlan>>() {
            }
            );

        } catch (Exception e) {
            log.error("OpenAI kategori planı JSON olarak parse edilemedi.", e);

            throw new IllegalStateException(
                    "OpenAI geçerli kategori planı JSON'u döndürmedi: " + content,
                    e
            );
        }
    }

    public OutfitSelection selectBestOutfit(String prompt) {

        OpenAiResponse response = openAiService.sendPrompt(
                prompt,
                null
        );

        String content = extractOutputText(response);

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(
                    content.trim(),
                    OutfitSelection.class
            );

        } catch (Exception e) {

            log.error(
                    "OpenAI outfit response JSON olarak parse edilemedi.",
                    e
            );

            throw new IllegalStateException(
                    "OpenAI geçerli outfit JSON'u döndürmedi: "
                    + content,
                    e
            );
        }
    }

    private String extractOutputText(OpenAiResponse response) {

        if (response == null
                || response.getOutput() == null
                || response.getOutput().isEmpty()) {

            throw new IllegalStateException(
                    "OpenAI response içinde output bulunamadı."
            );
        }

        for (Output output : response.getOutput()) {

            if (output == null
                    || output.getContent() == null
                    || output.getContent().isEmpty()) {
                continue;
            }

            for (Content item : output.getContent()) {

                if (item != null
                        && "output_text".equals(item.getType())) {

                    String text = item.getText();

                    if (text != null && !text.isBlank()) {
                        return text;
                    }
                }
            }
        }

        throw new IllegalStateException(
                "OpenAI response içinde output_text bulunamadı."
        );
    }
}
