package com.sneaksapp.knowledgebuilder.openai.response;

import lombok.Data;

import java.util.List;

//JSON'ın en dış katmanını temsil eder
@Data
public class OpenAiResponse {

    private List<Output> output;

}
