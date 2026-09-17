package com.sneaksapp.knowledgebuilder.openai.response;

import lombok.Data;

import java.util.List;

@Data
public class Output {

    private String type;

    private List<Content> content;

}