package com.jac.Springbootlibrary.dao;

import com.jac.Springbootlibrary.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    // return a list that save all the books checked out which are found by userEmail

    List<Checkout> findBooksByUserEmail(String userEmail);
}
