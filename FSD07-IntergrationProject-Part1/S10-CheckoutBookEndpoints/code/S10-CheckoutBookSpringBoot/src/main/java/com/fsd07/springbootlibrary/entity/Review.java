package com.fsd07.springbootlibrary.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Yeming Hu
 * @Date: 9/6/2023
 * @Description: com.fsd07.springbootlibrary.entity
 * @Version: 1.0
 */
@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "date")
    @CreationTimestamp
    private Date date;

    @Column(name = "rating")
    private double rating;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "review_description")
    private String reviewDescription;


}
