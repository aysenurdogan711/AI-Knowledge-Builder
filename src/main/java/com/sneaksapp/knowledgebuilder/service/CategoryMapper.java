package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.OutfitCategory;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper extends JsonAliasMapper<OutfitCategory> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/category-aliases.json",OutfitCategory.class);

    }

    public OutfitCategory map(String category) {

        return super.map(
                category,
                OutfitCategory.UNKNOWN);

    }

    public List<OutfitCategory> mapList(List<String> categories) {

        List<OutfitCategory> result = new ArrayList<>();

        if (categories == null || categories.isEmpty()) {
            return result;
        }

        for (String category : categories) {

            OutfitCategory mappedCategory = map(category);

            if (mappedCategory != OutfitCategory.UNKNOWN) {
                result.add(mappedCategory);
            }

        }

        return result;
    }

}
