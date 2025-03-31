package com.AyushToCode.LibraryServiceBackend.dao;


import com.AyushToCode.LibraryServiceBackend.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    List<Checkout> findBooksByUserEmail(String userEmail);

    @Modifying
    @Transactional
    @Query("delete from Checkout where bookId in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
