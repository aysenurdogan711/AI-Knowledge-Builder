package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.Formality;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class FormalityMapper extends JsonAliasMapper<Formality> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/formality-aliases.json",Formality.class);

    }

    public Formality map(String formality) {

        return super.map(formality,Formality.UNKNOWN);

    }

}