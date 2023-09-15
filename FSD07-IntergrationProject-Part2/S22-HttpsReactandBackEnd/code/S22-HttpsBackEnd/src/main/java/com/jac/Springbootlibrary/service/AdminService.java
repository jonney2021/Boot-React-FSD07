package com.jac.Springbootlibrary.service;

import com.jac.Springbootlibrary.dao.BookRepository;
import com.jac.Springbootlibrary.dao.CheckoutRepository;
import com.jac.Springbootlibrary.dao.ReviewRepository;
import com.jac.Springbootlibrary.entity.AddBookRequest;
import com.jac.Springbootlibrary.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {
    // inject BookRepository
    private BookRepository bookRepository;

    // inject CheckoutRepository and ReviewRepository for update its data after delete a book
    private ReviewRepository reviewRepository;

    private CheckoutRepository checkoutRepository;

    @Autowired
    public AdminService(BookRepository bookRepository,
                        ReviewRepository reviewRepository,
                        CheckoutRepository checkoutRepository
    ) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
    }

    //increase the number of the book
    public void increaseBookQuantity(Long bookId) throws Exception {
        //save the book which we found in our database by passing bookId.
        Optional<Book> book = bookRepository.findById(bookId);
        //if the book does not exist
        if (!book.isPresent()) {
            throw new Exception("Book not found");
        }
        // if exists, increase the number of copies, available copies by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);
        // save the book to the database.
        bookRepository.save(book.get());
    }

    // decrease increase the number of the book
    public void decreaseBookQuantity(Long bookId) throws Exception {
        //save the book which we found in our database by passing bookId.
        Optional<Book> book = bookRepository.findById(bookId);
        //if the book does not exist, or number of copies or copies available is not greater than 0
        if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book not found or quantity locked");
        }
        // if exists, and the number can be decreased, decrease them by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        book.get().setCopies(book.get().getCopies() - 1);
        // save the book to the database.
        bookRepository.save(book.get());
    }

    // delete book
    public void deleteBook(Long bookId) throws Exception {
        //save the book which we found in our database by passing bookId.
        Optional<Book> book = bookRepository.findById(bookId);
        // if not found
        if (!book.isPresent()) {
            throw new Exception("Book not found");
        }
        // if found, delete it from the bookRepository,
        // its review in reviewRepository, the checkout in checkoutRepository
        bookRepository.delete(book.get());
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
    // add book
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
