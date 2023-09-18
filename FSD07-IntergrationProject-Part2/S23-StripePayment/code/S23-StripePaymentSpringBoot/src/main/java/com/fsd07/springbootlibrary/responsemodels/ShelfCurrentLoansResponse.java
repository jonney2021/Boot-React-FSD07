package com.fsd07.springbootlibrary.responsemodels;

import com.fsd07.springbootlibrary.entity.Book;
import lombok.Data;

/**
 * @Author: Yeming Hu
 * @Date: 9/10/2023
 * @Description: com.fsd07.springbootlibrary.responsemodels
 * @Version: 1.0
 */

@Data
public class ShelfCurrentLoansResponse {
    public ShelfCurrentLoansResponse(Book book, int daysLeft){
        this.book = book;
        this.daysLeft = daysLeft;
    }

    private Book book;
    private int daysLeft;
}
