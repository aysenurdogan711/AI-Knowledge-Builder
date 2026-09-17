package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.ColorProfile;
import jakarta.annotation.PostConstruct;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class ColorMapper extends JsonAliasMapper<ColorProfile> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/color-aliases.json",ColorProfile.class);

    }

    public ColorProfile map(String color) {

        return super.map(
                color,
                ColorProfile.UNKNOWN);

    }

    public List<ColorProfile> mapList(List<String> colors) {

        List<ColorProfile> result = new ArrayList<>();

        if (colors == null || colors.isEmpty()) {
            return result;
        }

        for (String color : colors) {

            ColorProfile mappedColor = map(color);

            if (mappedColor != ColorProfile.UNKNOWN) {
                result.add(mappedColor);
            }

        }

        return result;
    }

}
