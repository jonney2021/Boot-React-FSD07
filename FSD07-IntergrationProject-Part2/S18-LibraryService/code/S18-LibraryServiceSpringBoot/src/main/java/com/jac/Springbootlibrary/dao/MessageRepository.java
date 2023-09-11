package com.jac.Springbootlibrary.dao;

import com.jac.Springbootlibrary.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageRepository extends JpaRepository<Message, Long>{

   // find all messages by user email and has a pagination functionality
    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);
}