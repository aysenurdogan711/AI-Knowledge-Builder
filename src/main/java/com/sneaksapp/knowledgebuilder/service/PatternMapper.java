package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.Pattern;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class PatternMapper extends JsonAliasMapper<Pattern> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/pattern-aliases.json",Pattern.class);

    }

    public Pattern map(String pattern) {

        return super.map(pattern,Pattern.UNKNOWN);

    }

}