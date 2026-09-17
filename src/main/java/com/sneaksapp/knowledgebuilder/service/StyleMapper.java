package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.Style;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class StyleMapper extends JsonAliasMapper<Style> {

    @PostConstruct
    public void init() {
        loadAliases("aliases/style-aliases.json",Style.class);

    }

    public Style map(String style) {

        return super.map(style,Style.UNKNOWN);

    }

}
