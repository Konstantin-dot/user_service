package com.aston.controller;

import com.aston.dto.EmailRequest;
import com.aston.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String send(@RequestBody EmailRequest request) {

        emailService.send(
                request.getTo(),
                request.getSubject(),
                request.getText()
        );

        return "Email sent";
    }
}