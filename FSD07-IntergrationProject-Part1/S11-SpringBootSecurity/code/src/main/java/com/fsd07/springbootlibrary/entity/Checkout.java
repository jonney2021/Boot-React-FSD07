package com.fsd07.springbootlibrary.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Yeming Hu
 * @Date: 9/9/2023
 * @Description: com.fsd07.springbootlibrary.entity
 * @Version: 1.0
 */
@Entity
@Table(name="checkout")
@Data
public class Checkout {
    public Checkout(){}

    public Checkout(String userEmail, String checkoutData, String returnDate, Long bookId){
        this.userEmail = userEmail;
        this.checkoutDate = checkoutData;
        this.returnDate = returnDate;
        this.bookId = bookId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "return_date")
    private String returnDate;

    @Column(name = "book_id")
    private Long bookId;
}
