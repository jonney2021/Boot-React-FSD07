package com.fsd07.springbootlibrary.dao;

import com.fsd07.springbootlibrary.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Yeming Hu
 * @Date: 9/3/2023
 * @Description: com.fsd07.springbootlibrary.dao
 * @Version: 1.0
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    // Retrieves a Page of books containing the specified title.
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    // Retrieves a Page of books for the specified category.
    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);

    // Custom query to retrieve a list of books based on provided book IDs.
    @Query("select o from Book o where id in :book_ids")
    List<Book> findBooksByBookIds (@Param("book_ids") List<Long> bookId);
}
