package com.fsd07.springbootlibrary.dao;

import com.fsd07.springbootlibrary.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Yeming Hu
 * @Date: 9/11/2023
 * @Description: com.fsd07.springbootlibrary.dao
 * @Version: 1.0
 */
// Create Message Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Add interface for search all messages of a specific user in MessageRepository
    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);

    // Add interface for search all closed or open messages
    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);

}
