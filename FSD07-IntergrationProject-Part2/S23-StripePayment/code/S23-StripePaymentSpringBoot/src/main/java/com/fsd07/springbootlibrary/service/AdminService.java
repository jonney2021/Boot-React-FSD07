package com.fsd07.springbootlibrary.service;

import com.fsd07.springbootlibrary.dao.BookRepository;
import com.fsd07.springbootlibrary.dao.CheckoutRepository;
import com.fsd07.springbootlibrary.dao.ReviewRepository;
import com.fsd07.springbootlibrary.entity.Book;
import com.fsd07.springbootlibrary.entity.Review;
import com.fsd07.springbootlibrary.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: Yeming Hu
 * @Date: 9/12/2023
 * @Description: com.fsd07.springbootlibrary.service
 * @Version: 1.0
 */

@Service
@Transactional
public class AdminService {

    // inject BookRepository
    private BookRepository bookRepository;

    // inject CheckoutRepository and ReviewRepository for update its data after delete a book
    private ReviewRepository reviewRepository;
    private CheckoutRepository checkoutRepository;

    @Autowired
    public AdminService(BookRepository bookRepository, ReviewRepository reviewRepository, CheckoutRepository checkoutRepository){
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
    }

    //increase the number of the book
    public void increaseBookQuantity(Long bookId) throws Exception{
        //save the book which we found in our database by passing bookId.
        Optional<Book> book = bookRepository.findById(bookId);
        //if the book does not exist
        if(!book.isPresent()){
            throw new Exception("Book not found");
        }

        // if exists, increase the number of copies, available copies by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() +1);
        book.get().setCopies(book.get().getCopies()+1);

        // save the book to the database.
        bookRepository.save(book.get());
    }

    // decrease increase the number of the book
    public void decreaseBookQuantity(Long bookId) throws Exception{
        //save the book which we found in our database by passing bookId.
        Optional<Book> book = bookRepository.findById(bookId);
        //if the book does not exist, or number of copies or copies available is not greater than 0
        if(!book.isPresent() || book.get().getCopiesAvailable() <=0 || book.get().getCopies() <= 0){
            throw new Exception("Book not found or quantity locked");

        }
        // if exists, and the number can be decreased, decrease them by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
        book.get().setCopies(book.get().getCopies()-1);

        // save the book to the database.
        bookRepository.save(book.get());
    }

    // add book
    public void postBook(AddBookRequest addBookRequest){
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

    // delete book
    public void deleteBook(Long bookId) throws Exception{
        //save the book which we found in our database by passing bookId.
        Optional<Book> book = bookRepository.findById(bookId);

        // if not found
        if(!book.isPresent()){
            throw new Exception("Book not found");
        }
        // if found, delete it from the bookRepository,
        // its review in reviewRepository, the checkout in checkoutRepository
        bookRepository.delete(book.get());
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
}
