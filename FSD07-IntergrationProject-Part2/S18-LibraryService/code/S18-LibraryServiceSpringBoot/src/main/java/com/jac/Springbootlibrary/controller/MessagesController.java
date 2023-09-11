package com.jac.Springbootlibrary.controller;


import com.jac.Springbootlibrary.entity.Message;
import com.jac.Springbootlibrary.service.MessagesService;
import com.jac.Springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

     // inject MessagesService
    private MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    // post a message
    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        // get user email from token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        //call postMessage() in messagesService to save the message to our database
        messagesService.postMessage(messageRequest, userEmail);
    }
}
