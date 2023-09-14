package com.jac.Springbootlibrary.dao;

import com.jac.Springbootlibrary.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //find by book Id

    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);
    // find by userEmail and Book Id
    Review findByUserEmailAndBookId(String userEmail, Long bookId);

    // if a book is deleted, we also need to delete its review in review repository.
    @Modifying
    @Query("delete from Review where book_id in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
