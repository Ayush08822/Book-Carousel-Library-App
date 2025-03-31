package com.AyushToCode.LibraryServiceBackend.dao;

import com.AyushToCode.LibraryServiceBackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , Long> {

    Payment findByUserEmail(@RequestParam(name = "userEmail") String userEmail);
}
