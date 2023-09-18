package com.fsd07.springbootlibrary.dao;

import com.fsd07.springbootlibrary.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Yeming Hu
 * @Date: 9/12/2023
 * @Description: com.fsd07.springbootlibrary.dao
 * @Version: 1.0
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // find a Payment by the user's email.
    Payment findByUserEmail(String userEmail);
}
