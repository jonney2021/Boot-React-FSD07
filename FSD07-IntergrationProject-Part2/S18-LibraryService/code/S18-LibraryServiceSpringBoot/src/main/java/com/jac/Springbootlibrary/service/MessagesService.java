package com.jac.Springbootlibrary.service;

import com.jac.Springbootlibrary.dao.MessageRepository;
import com.jac.Springbootlibrary.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessagesService {
    // inject MessageRepository
    private MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //post message
    public void postMessage(Message messageRequest, String userEmail) {
        // get the message from the request and save it into messageRepository
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

}
