package com.fsd07.springbootlibrary.controller;

import com.fsd07.springbootlibrary.entity.Book;
import com.fsd07.springbootlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yeming Hu
 * @Date: 9/9/2023
 * @Description: com.fsd07.springbootlibrary.controller
 * @Version: 1.0
 */
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoandCount(){
        String userEmail = "testuser@email.com";
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckout/byuser")
    public Boolean checkoutBookByUser(@RequestParam Long bookId){
        String userEmail = "testuser@email.com";
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception{
        String userEmail = "testuser@email.com";
        return bookService.checkoutBook(userEmail, bookId);
    }
}
