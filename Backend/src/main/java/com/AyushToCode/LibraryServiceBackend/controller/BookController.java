package com.AyushToCode.LibraryServiceBackend.controller;


import com.AyushToCode.LibraryServiceBackend.entity.Book;
import com.AyushToCode.LibraryServiceBackend.responsemodels.ShelfCurrentLoansResponse;
import com.AyushToCode.LibraryServiceBackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    public ResponseEntity<List<ShelfCurrentLoansResponse>> currentLoans(@AuthenticationPrincipal Jwt jwt)
        throws Exception
    {
        String userEmail = jwt.getClaimAsString("email");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = bookService.currentLoans(userEmail);
        return new ResponseEntity<>(shelfCurrentLoansResponses , HttpStatus.OK);
    }

    @GetMapping("/secure/currentloans/count")
    public ResponseEntity<Integer> currentLoansCount(@AuthenticationPrincipal Jwt jwt) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        Integer LoansCount = bookService.currentLoansCount(userEmail);
        return new ResponseEntity<>(LoansCount , HttpStatus.OK);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public ResponseEntity<Boolean> checkoutBookByUser(@AuthenticationPrincipal Jwt jwt,
                                      @RequestParam(value = "bookId") Long bookId) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return new ResponseEntity<>(bookService.checkoutBookByUser(userEmail, bookId) , HttpStatus.OK);
    }

    @PostMapping("/secure/checkout")
    public ResponseEntity<Book> checkoutBook (@AuthenticationPrincipal Jwt jwt,
                              @RequestParam(value = "bookId") Long bookId) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return new ResponseEntity<>(bookService.checkoutBook(userEmail, bookId) , HttpStatus.OK);
    }

    @PutMapping("/secure/return")
    public void returnBook(@AuthenticationPrincipal Jwt jwt,
                           @RequestParam(value = "bookId") Long bookId) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@AuthenticationPrincipal Jwt jwt,
                          @RequestParam Long bookId) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        bookService.renewLoan(userEmail, bookId);
    }

}












