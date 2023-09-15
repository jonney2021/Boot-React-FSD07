package com.jac.Springbootlibrary.service;


import com.jac.Springbootlibrary.dao.BookRepository;
import com.jac.Springbootlibrary.dao.ReviewRepository;
import com.jac.Springbootlibrary.entity.Review;
import com.jac.Springbootlibrary.requestModels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {
    //inject two repositories

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService( ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        // find a review by user email and bookId
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
        // if found,means  the review already created by this user.
        if (validateReview != null) {
            throw new Exception("Review already created");
        }
        // save the data of the review Request
        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        // if there is a review description, then change the data from the optional object to our string
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        //set the review date
        review.setDate(Date.valueOf(LocalDate.now()));
        //save the review into our review repository
        reviewRepository.save(review);
    }
    //check if a user has dropped a review for the specified review
    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateReview != null) {
            return true;
        } else {
            return false;
        }
    }

}
