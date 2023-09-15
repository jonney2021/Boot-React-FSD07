package com.jac.Springbootlibrary.service;

import com.jac.Springbootlibrary.dao.MessageRepository;
import com.jac.Springbootlibrary.entity.Message;
import com.jac.Springbootlibrary.requestModels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        // update current message with admin's response
    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        // go to message repository to find the specified message by the in the admin question request
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        // if it does not exist, throw an error
        if (!message.isPresent()) {
            throw new Exception("Message not found");
        }
        //update the message's properties
        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }

}
