package com.sneaksapp.knowledgebuilder.model;

import com.sneaksapp.knowledgebuilder.model.enums.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutfitItem {

private OutfitCategory category;

private ColorProfile color;

private Material material;

private Pattern pattern;

private Fit fit;
}