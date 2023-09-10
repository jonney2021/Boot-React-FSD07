package com.fsd07.springbootlibrary.controller;

import com.fsd07.springbootlibrary.entity.Book;
import com.fsd07.springbootlibrary.service.BookService;
import com.fsd07.springbootlibrary.utils.ExtractJWT;
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
    public int currentLoansCount(@RequestHeader(value="Authorization") String token){
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }


    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId){
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }
}
