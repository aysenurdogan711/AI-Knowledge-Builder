package com.sneaksapp.knowledgebuilder.openai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiKnowledgeResponse {

    private List<String> outfitCategories;

    private List<String> colors;

    private String material;

    private String pattern;

    private String fit;

    private String season;

    private String style;

    private String formality;

    private String summary;

}