package com.fsd07.springbootlibrary.controller;

import com.fsd07.springbootlibrary.requestmodels.ReviewRequest;
import com.fsd07.springbootlibrary.service.ReviewService;
import com.fsd07.springbootlibrary.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yeming Hu
 * @Date: 9/10/2023
 * @Description: com.fsd07.springbootlibrary.controller
 * @Version: 1.0
 */

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    // inject ReviewService
    private ReviewService reviewService;

    // ReviewService constructor
    public ReviewController (ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token,
                                    @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if(userEmail == null){
            throw new Exception("User email is missing!");
        }
        return reviewService.userReviewListed(userEmail,bookId);
    }

    // use PostMapping to save the review
    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if(userEmail == null){
            throw new Exception("USer email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
}
