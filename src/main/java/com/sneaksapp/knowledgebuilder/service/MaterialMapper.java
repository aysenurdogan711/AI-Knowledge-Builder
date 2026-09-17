package com.sneaksapp.knowledgebuilder.service;

import com.sneaksapp.knowledgebuilder.model.enums.Material;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class MaterialMapper extends JsonAliasMapper<Material> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/material-aliases.json",Material.class);

    }

    public Material map(String material) {

        return super.map(material,Material.UNKNOWN);

    }

}
