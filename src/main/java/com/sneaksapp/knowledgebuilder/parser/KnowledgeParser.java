package com.sneaksapp.knowledgebuilder.parser;

import com.sneaksapp.knowledgebuilder.model.Knowledge;
import com.sneaksapp.knowledgebuilder.model.enums.ColorProfile;
import com.sneaksapp.knowledgebuilder.openai.response.*;
import com.sneaksapp.knowledgebuilder.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import java.util.*;


@Component
@Slf4j
public class KnowledgeParser {

    private final CategoryMapper categoryMapper;
    private final ColorMapper colorMapper;
    private final MaterialMapper materialMapper;
    private final PatternMapper patternMapper;
    private final FitMapper fitMapper;
    private final SeasonMapper seasonMapper;
    private final StyleMapper styleMapper;
    private final FormalityMapper formalityMapper;

    public KnowledgeParser(
            CategoryMapper categoryMapper,
            ColorMapper colorMapper,
            MaterialMapper materialMapper,
            PatternMapper patternMapper,
            FitMapper fitMapper,
            SeasonMapper seasonMapper,
            StyleMapper styleMapper,
            FormalityMapper formalityMapper) {

        this.categoryMapper = categoryMapper;
        this.colorMapper = colorMapper;
        this.materialMapper = materialMapper;
        this.patternMapper = patternMapper;
        this.fitMapper = fitMapper;
        this.seasonMapper = seasonMapper;
        this.styleMapper = styleMapper;
        this.formalityMapper = formalityMapper;
    }

    public Knowledge parse(OpenAiResponse response) {

        try {

            String json = extractJson(response);

            ObjectMapper mapper =new ObjectMapper();

            AiKnowledgeResponse aiResponse =mapper.readValue(
                            json,
                            AiKnowledgeResponse.class
                    );

            log.debug("Knowledge AI color: {} -> {}",
                    aiResponse.getColors(),
                    colorMapper.mapList(
                            aiResponse.getColors()
                    )
            );

            log.debug("Knowledge AI pattern: {} -> {}",
                    aiResponse.getPattern(),
                    patternMapper.map(
                            aiResponse.getPattern()
                    )
            );

            log.debug("Knowledge AI material: {} -> {}",
                    aiResponse.getMaterial(),
                    materialMapper.map(
                            aiResponse.getMaterial()
                    )
            );

            log.debug("Knowledge AI fit: {} -> {}",
                    aiResponse.getFit(),
                    fitMapper.map(
                            aiResponse.getFit()
                    )
            );

            log.debug("Knowledge AI category: {} -> {}",
                    aiResponse.getOutfitCategories(),
                    categoryMapper.mapList(
                            aiResponse.getOutfitCategories()
                    )
            );

            List<ColorProfile> mappedColors =colorMapper.mapList(
                            aiResponse.getColors()
                    );

            ColorProfile primaryColor =mappedColors.isEmpty()
                            ? ColorProfile.UNKNOWN
                            : mappedColors.get(0);

            List<ColorProfile> secondaryColors =mappedColors.size() <= 1
                            ? new ArrayList<>()
                            : new ArrayList<>(
                                    mappedColors.subList(
                                            1,
                                            mappedColors.size()
                                    )
                            );

            return Knowledge.builder()
                    .outfitCategories(
                            categoryMapper.mapList(
                                    aiResponse.getOutfitCategories()
                            )
                    )
                    .primaryColor(primaryColor)
                    .secondaryColors(secondaryColors)
                    .material(
                            materialMapper.map(aiResponse.getMaterial()
                            )
                    )
                    .pattern(
                            patternMapper.map(aiResponse.getPattern()
                            )
                    )
                    .fit(
                            fitMapper.map(aiResponse.getFit()
                            )
                    )
                    .season(
                            seasonMapper.map(aiResponse.getSeason()
                            )
                    )
                    .style(
                            styleMapper.map(aiResponse.getStyle()
                            )
                    )
                    .formality(
                            formalityMapper.map(aiResponse.getFormality()
                            )
                    )
                    .summary(aiResponse.getSummary()
                    )
                    .build();

        } catch (Exception e) {

            log.error("Knowledge parsing başarısız.",e);

            throw new RuntimeException("Knowledge parsing failed.",e);
        }
    }

    private String extractJson(OpenAiResponse response) {

        if (response.getOutput() == null) {

            throw new RuntimeException("OpenAI response is empty.");
        }

        for (Output output : response.getOutput()) {

            if (output.getContent() == null) {
                continue;
            }

            for (Content content : output.getContent()) {

                if ("output_text".equals(content.getType())) {

                    return content.getText();
                }
            }
        }

        throw new RuntimeException("JSON response not found."
        );
    }

    private String getField(
            String response,
            String fieldName) {

        for (String line :response.split("\\R")) {

            if (line.startsWith(fieldName + ":")) {

                return line.substring(
                        fieldName.length() + 1).trim();
            }
        }

        return "";
    }
}