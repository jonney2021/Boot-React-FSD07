package com.fsd07.springbootlibrary.dao;

import com.fsd07.springbootlibrary.entity.History;
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
public interface HistoryRepository extends JpaRepository<History, Long> {

     // retrieves a Page of History entities associated with a specific user email.
    Page<History> findBooksByUserEmail(@RequestParam("email") String userEmail, Pageable pageable);
}
