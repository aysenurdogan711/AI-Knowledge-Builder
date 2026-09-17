package com.sneaksapp.knowledgebuilder.openai.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiRequest {

    private String model;

    private Object input;

}
