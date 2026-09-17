package com.sneaksapp.knowledgebuilder.service
        ;

import com.sneaksapp.knowledgebuilder.model.enums.Fit;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class FitMapper extends JsonAliasMapper<Fit> {

    @PostConstruct
    public void init() {

        loadAliases("aliases/fit-aliases.json",Fit.class);

    }

    public Fit map(String fit) {

        return super.map(fit,Fit.UNKNOWN);

    }

}
