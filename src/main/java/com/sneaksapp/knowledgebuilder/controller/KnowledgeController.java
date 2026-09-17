package com.sneaksapp.knowledgebuilder.controller;

import com.sneaksapp.knowledgebuilder.generator.KnowledgeGenerationService;
import com.sneaksapp.knowledgebuilder.model.Knowledge;
import com.sneaksapp.knowledgebuilder.model.OutfitCategoryPlan;
import com.sneaksapp.knowledgebuilder.model.OutfitSelection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeGenerationService knowledgeGenerationService;

    @PostMapping("/generate")
    public Knowledge generate(
            @RequestBody Map<String, String> request) {

        String imageUrl = request.get("imageUrl");

        return knowledgeGenerationService.generate(imageUrl);
    }

@PostMapping("/select-categories")
public List<OutfitCategoryPlan> selectOutfitCategories(
        @RequestBody Map<String, String> request) {

    String prompt = request.get("prompt");

    return knowledgeGenerationService.selectOutfitCategories(prompt);
}

@PostMapping("/select")
public OutfitSelection selectBestOutfit(
        @RequestBody Map<String, String> request) {

    String prompt = request.get("prompt");

    return knowledgeGenerationService.selectBestOutfit(prompt);
}
}