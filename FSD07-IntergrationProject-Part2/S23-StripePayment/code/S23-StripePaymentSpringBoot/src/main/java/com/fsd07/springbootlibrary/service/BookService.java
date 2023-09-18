package com.fsd07.springbootlibrary.service;

import com.fsd07.springbootlibrary.dao.BookRepository;
import com.fsd07.springbootlibrary.dao.CheckoutRepository;
import com.fsd07.springbootlibrary.dao.HistoryRepository;
import com.fsd07.springbootlibrary.dao.PaymentRepository;
import com.fsd07.springbootlibrary.entity.Book;
import com.fsd07.springbootlibrary.entity.Checkout;
import com.fsd07.springbootlibrary.entity.History;
import com.fsd07.springbootlibrary.entity.Payment;
import com.fsd07.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yeming Hu
 * @Date: 9/9/2023
 * @Description: com.fsd07.springbootlibrary.service
 * @Version: 1.0
 */

@Service
@Transactional
public class BookService {

    // Fields for repositories
    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    private PaymentRepository paymentRepository;

    // Constructor for dependency injection
    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,HistoryRepository historyRepository,PaymentRepository paymentRepository){
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception{
        // get a book by its Id from database
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail,bookId);
        // check if the book is not in our database, user already has it checked out  or other users have borrowed all the copies
        if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable()<=0){
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        // Get the list of books currently checked out by the user
        List<Checkout> currentBooksCheckedOut = checkoutRepository.findBooksByUserEmail(userEmail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean bookNeedsReturned = false;

        // Check if the user has any overdue books
        for(Checkout checkout: currentBooksCheckedOut){
            Date d1 = sdf.parse(checkout.getReturnDate());
            Date d2 = sdf.parse(LocalDate.now().toString());

            TimeUnit time = TimeUnit.DAYS;
            double differenceInTime = time.convert(d1.getTime()-d2.getTime(), TimeUnit.MILLISECONDS);
            if(differenceInTime < 0){
                bookNeedsReturned = true;
                break;
            }
        }
        // Check if the user has outstanding fees
        Payment userPayment = paymentRepository.findByUserEmail(userEmail);
        if((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && bookNeedsReturned)){
            throw new Exception("Outstanding fees");
        }

        // Create a new payment record if it doesn't exist for the user
        if(userPayment == null){
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }
        // if it exists and user has not borrowed yet, and there are still copies available, we add it to our book repository
        // and decrease its number of copies available by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
        bookRepository.save(book.get());

        // save the book into checkout repository
        Checkout checkout = new Checkout(userEmail, LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),book.get().getId());
        checkoutRepository.save(checkout);
        return book.get();
    }


    public Boolean checkoutBookByUser(String userEmail, Long bookId){
        // Check if there is a checkout record for the specified user and book.
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        // If a checkout record exists for the user and book, return true; otherwise, return false.
        if(validateCheckout != null){
            return true;
        }else{
            return false;
        }
    }

    public int currentLoansCount(String userEmail){
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception{
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        // extract all the current loans books by user email
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);

        List<Long> bookIdList = new ArrayList<>();
        //loop the checkout list ,extract all of the book IDs and save them into bookIdList
        for(Checkout i : checkoutList){
            bookIdList.add(i.getBookId());
        }
        // Passing bookIdList, find all the books in our bookRepository and save them into our books list.
        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

        // specify the date pattern
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // loop books,check if there is a book's Id that matches the Id in our checkout list
        for(Book book: books){
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x->x.getBookId() == book.getId()).findFirst();
            //check if checkout exists
            if(checkout.isPresent()){
                // parse the return date into our specified pattern
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                // parse the today's date into our specified pattern
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;
                // calculate the difference and convert it to days
                long difference_In_Time = time.convert(d1.getTime()-d2.getTime(), TimeUnit.MILLISECONDS);
                // passing the book and the difference of time(convert it to integer) to our shelfCurrentLoansResponses
                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book,(int)difference_In_Time));
            }
        }
        return shelfCurrentLoansResponses;
    }

    public void returnBook(String userEmail, Long bookId) throws Exception{
        // grab the book by book Id
        Optional<Book> book = bookRepository.findById(bookId);
        // grab the checkout  by user email and book id
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail,bookId);
        // if not exists:
        if(!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user.");
        }
        //make the number of copies available increase by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()+1);
        // save the book into our book repository
        bookRepository.save(book.get());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        TimeUnit time = TimeUnit.DAYS;
        double differenceInTime = time.convert(d1.getTime()-d2.getTime(), TimeUnit.MILLISECONDS);

        if(differenceInTime < 0){
            Payment payment = paymentRepository.findByUserEmail(userEmail);
            payment.setAmount(payment.getAmount()+(differenceInTime * -1));
            paymentRepository.save(payment);
        }
        //delete the book from our checkout database
        checkoutRepository.deleteById(validateCheckout.getId());

        // save a new history object to our database
        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg()
        );
        historyRepository.save(history);
    }

    // renew book
    public void renewLoan(String userEmail, Long bookId) throws Exception{
        //find the book by user email and id and save it into validateCheckout.
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        // if not found
        if(validateCheckout == null){
            throw new Exception("Book does not exist or not checked out by user.");
        }
        // set the date pattern
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        // convert the due date into our data pattern
        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        // convert the today date into our data pattern
        Date d2 = sdFormat.parse(LocalDate.now().toString());
        // if not exceed the due date, renew another 7 days
        if(d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0){
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }
 }
