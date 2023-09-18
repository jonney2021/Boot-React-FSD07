package com.fsd07.springbootlibrary.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Yeming Hu
 * @Date: 9/12/2023
 * @Description: com.fsd07.springbootlibrary.entity
 * @Version: 1.0
 */

@Entity
@Table(name="payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "amount")
    private double amount;


}
