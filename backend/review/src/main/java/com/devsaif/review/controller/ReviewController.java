package com.devsaif.review.controller;

import com.devsaif.review.dto.ApiResponse;
import com.devsaif.review.dto.ReviewRequest;
import com.devsaif.review.dto.SalonDTO;
import com.devsaif.review.dto.UserDTO;
import com.devsaif.review.model.Review;
import com.devsaif.review.service.ReviewService;
import com.devsaif.review.service.client.SalonFeignClient;
import com.devsaif.review.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/reviews/")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(
            @RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long salonId
    ) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(req, user, salon);

        return ResponseEntity.ok().body(review);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Review>> getReviewsBySalonId(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long salonId
    ) throws Exception {

        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        List<Review> reviews = reviewService.getReviewsBySalonId(salonId);

        return ResponseEntity.ok().body(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest req
            ) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        Review review = reviewService.updateReview(
                req,
                reviewId,
                user.getId()
                );

        return ResponseEntity.ok().body(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long reviewId
    ) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        reviewService.deleteReview(reviewId, user.getId());

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("Deleted Review");

        return ResponseEntity.ok(apiResponse);
    }

}
