package com.fsd07.springbootlibrary.dao;

import com.fsd07.springbootlibrary.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Yeming Hu
 * @Date: 9/6/2023
 * @Description: com.fsd07.springbootlibrary.dao
 * @Version: 1.0
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId,
                              Pageable pageable);
}
