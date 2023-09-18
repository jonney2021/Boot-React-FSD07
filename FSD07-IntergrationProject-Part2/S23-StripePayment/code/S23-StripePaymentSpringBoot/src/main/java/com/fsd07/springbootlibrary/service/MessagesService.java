package com.fsd07.springbootlibrary.service;

import com.fsd07.springbootlibrary.dao.MessageRepository;
import com.fsd07.springbootlibrary.entity.Message;
import com.fsd07.springbootlibrary.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: Yeming Hu
 * @Date: 9/11/2023
 * @Description: com.fsd07.springbootlibrary.service
 * @Version: 1.0
 */

@Service
@Transactional
public class MessagesService {
    // inject MessageRepository
    private MessageRepository messageRepository;

    @Autowired
    public void MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    //post message
    public void postMessage(Message messageRequest, String userEmail){
        // get the message from the request and save it into messageRepository
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    // update current message with admin's response
    public void putMessage(AdminQuestionRequest adminQuestionRequest,String userEmail) throws Exception{
        // go to message repository to find the specified message by the in the admin question request
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        // if it does not exist, throw an error
        if(!message.isPresent()){
            throw new Exception("Message not found");
        }
        //update the message's properties
        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }
}
