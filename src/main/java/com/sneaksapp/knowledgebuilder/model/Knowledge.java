package com.sneaksapp.knowledgebuilder.model;

import com.sneaksapp.knowledgebuilder.model.enums.ColorProfile;
import com.sneaksapp.knowledgebuilder.model.enums.Fit;
import com.sneaksapp.knowledgebuilder.model.enums.Formality;
import com.sneaksapp.knowledgebuilder.model.enums.Material;
import com.sneaksapp.knowledgebuilder.model.enums.OutfitCategory;
import com.sneaksapp.knowledgebuilder.model.enums.Pattern;
import com.sneaksapp.knowledgebuilder.model.enums.Season;
import com.sneaksapp.knowledgebuilder.model.enums.Style;
import java.util.List;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge {

    private List<OutfitCategory> outfitCategories;

    private ColorProfile primaryColor;

    private List<ColorProfile> secondaryColors;

    private Material material;

    private Pattern pattern;

    private Fit fit;

    private Style style;

    private Formality formality;

    private Season season;

    private String summary;
}