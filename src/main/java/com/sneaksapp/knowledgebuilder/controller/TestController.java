package com.sneaksapp.knowledgebuilder.controller;

import com.sneaksapp.knowledgebuilder.model.enums.ColorProfile;
import com.sneaksapp.knowledgebuilder.service.ColorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final ColorMapper colorMapper;

    @GetMapping("/color")
    public ColorProfile test(@RequestParam String value) {

        return colorMapper.map(value);

    }

}