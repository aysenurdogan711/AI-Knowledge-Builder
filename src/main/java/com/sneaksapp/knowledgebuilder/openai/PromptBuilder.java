package com.sneaksapp.knowledgebuilder.openai;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {
    
    public String loadPrompt(String fileName){
        
        try{
            //prompts file'ındaki dosyaları okumamızı sağlar
            ClassPathResource resource= new ClassPathResource(
                                       "prompts/"+fileName);
            
            return new String(resource.getInputStream()
                    .readAllBytes()
                    ,StandardCharsets.UTF_8);
        
        }catch(IOException e){
            throw new RuntimeException(
                    "Prompt could not be loaded"+fileName,
                     e);
        }     
        
    }
}
