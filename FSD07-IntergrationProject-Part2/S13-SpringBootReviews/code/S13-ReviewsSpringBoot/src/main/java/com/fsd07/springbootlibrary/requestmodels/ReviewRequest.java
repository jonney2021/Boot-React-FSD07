package com.fsd07.springbootlibrary.requestmodels;

import lombok.Data;

import java.util.Optional;

/**
 * @Author: Yeming Hu
 * @Date: 9/10/2023
 * @Description: com.fsd07.springbootlibrary.requestmodels
 * @Version: 1.0
 */

@Data
public class ReviewRequest {
    private double rating;

    private Long bookId;

    private Optional<String> reviewDescription;
}
