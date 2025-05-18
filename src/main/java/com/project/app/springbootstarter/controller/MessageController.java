package com.project.app.springbootstarter.controller;

import com.project.app.springbootstarter.dto.MessageDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/message")
    public MessageDTO getMessage() {
        return MessageDTO.builder().message("Test message.").build();
    }

}
