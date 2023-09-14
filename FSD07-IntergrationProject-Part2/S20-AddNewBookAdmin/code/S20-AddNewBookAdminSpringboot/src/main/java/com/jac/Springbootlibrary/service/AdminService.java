package com.jac.Springbootlibrary.service;

import com.jac.Springbootlibrary.dao.BookRepository;
import com.jac.Springbootlibrary.dao.CheckoutRepository;
import com.jac.Springbootlibrary.dao.ReviewRepository;
import com.jac.Springbootlibrary.entity.AddBookRequest;
import com.jac.Springbootlibrary.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
    // inject BookRepository
    private BookRepository bookRepository;


    @Autowired
    public AdminService(BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }
    // get book info from AddBookRequest,save the book to our bookRepository
    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }
}
