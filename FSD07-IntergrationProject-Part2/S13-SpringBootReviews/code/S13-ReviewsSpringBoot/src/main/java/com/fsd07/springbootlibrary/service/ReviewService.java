package com.fsd07.springbootlibrary.service;

import com.fsd07.springbootlibrary.dao.BookRepository;
import com.fsd07.springbootlibrary.dao.ReviewRepository;
import com.fsd07.springbootlibrary.entity.Review;
import com.fsd07.springbootlibrary.requestmodels.ReviewRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @Author: Yeming Hu
 * @Date: 9/10/2023
 * @Description: com.fsd07.springbootlibrary.service
 * @Version: 1.0
 */

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception
    {
        // find a review by user email and bookId
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
        // if found,means the review already created by this user.
        if(validateReview != null){
            throw new Exception("Review already created");
        }
        // save the data of the review Request
        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        // if there is a review description, then change the data from the optional object to our string
        if(reviewRequest.getReviewDescription().isPresent()){
            // use map() to copy an optional to string
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        // here Date is from java.sql.Date, we're going to save this into database
        review.setDate(Date.valueOf(LocalDate.now()));
        // save review record into database
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        if(validateReview != null){
            return true;
        }else{
            return false;
        }
    }
}
