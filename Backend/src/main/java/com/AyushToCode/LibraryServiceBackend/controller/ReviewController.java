package com.AyushToCode.LibraryServiceBackend.controller;


import com.AyushToCode.LibraryServiceBackend.requestmodels.ReviewRequest;
import com.AyushToCode.LibraryServiceBackend.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController (ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/book")
    public ResponseEntity<Boolean> reviewBookByUser(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestParam(value = "bookId") Long bookId) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        System.out.println(userEmail);
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return new ResponseEntity<>(reviewService.userReviewListed(userEmail, bookId) , HttpStatus.OK);
    }

    @PostMapping("/secure")
    public void postReview(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        System.out.println(userEmail);
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
}
