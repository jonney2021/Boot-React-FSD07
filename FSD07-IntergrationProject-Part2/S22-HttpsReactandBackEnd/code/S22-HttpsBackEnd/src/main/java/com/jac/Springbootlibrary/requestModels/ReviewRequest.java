package com.jac.Springbootlibrary.requestModels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    private double rating;

    private Long bookId;
   // why Optional: review description is not required when leaving a review for a book.
    private Optional<String> reviewDescription;
}
