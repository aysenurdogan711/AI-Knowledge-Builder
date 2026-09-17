package com.sneaksapp.knowledgebuilder.openai.response;

import lombok.Data;

//OpenAI'ın ürettiği metni taşır
@Data
public class Content {

    private String type;

    private String text;

}