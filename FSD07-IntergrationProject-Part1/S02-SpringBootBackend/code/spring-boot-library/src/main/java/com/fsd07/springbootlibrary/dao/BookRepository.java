package com.fsd07.springbootlibrary.dao;

import com.fsd07.springbootlibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Yeming Hu
 * @Date: 9/3/2023
 * @Description: com.fsd07.springbootlibrary.dao
 * @Version: 1.0
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
