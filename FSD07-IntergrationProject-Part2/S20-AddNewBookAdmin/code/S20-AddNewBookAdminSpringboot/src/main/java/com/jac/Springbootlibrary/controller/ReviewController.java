package com.jac.Springbootlibrary.controller;

import com.jac.Springbootlibrary.requestModels.ReviewRequest;
import com.jac.Springbootlibrary.service.ReviewService;
import com.jac.Springbootlibrary.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    // inject ReviewService
    private ReviewService reviewService;
    // ReviewService constructor
    public ReviewController (ReviewService reviewService) {
        this.reviewService = reviewService;
    }

   // use PostMapping to save the review
    @PostMapping("/secure")
    public void postReview(@RequestHeader(value="Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
    // check if the user has left a review for the specified book, if exists, return ture, if not ,false
    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token,
                                    @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }
}
