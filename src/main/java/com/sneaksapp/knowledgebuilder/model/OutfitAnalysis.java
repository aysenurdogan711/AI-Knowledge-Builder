package com.sneaksapp.knowledgebuilder.model;

import com.sneaksapp.knowledgebuilder.model.enums.Formality;
import com.sneaksapp.knowledgebuilder.model.enums.Season;
import java.text.ListFormat.Style;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OutfitAnalysis {

    private Style style;

    private Season season;

    private Formality formality;

    private List<OutfitItem> items;

}
