package com.company.controller;

import com.company.dto.ChatGptRequest;
import com.company.dto.ChatGptResponse;
import com.company.form.RequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestBody RequestForm form){

        ChatGptRequest request = new ChatGptRequest(model, form.getPrompt());
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);

        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
