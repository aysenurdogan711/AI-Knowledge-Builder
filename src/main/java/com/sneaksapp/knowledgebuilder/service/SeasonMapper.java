package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.Season;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class SeasonMapper extends JsonAliasMapper<Season> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/season-aliases.json",Season.class);

    }

    public Season map(String season) {

        return super.map(season,Season.UNKNOWN);

    }

}
