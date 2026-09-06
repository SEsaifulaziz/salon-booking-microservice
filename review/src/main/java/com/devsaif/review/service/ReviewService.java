package com.devsaif.review.service;


import com.devsaif.review.dto.ReviewRequest;
import com.devsaif.review.dto.SalonDTO;
import com.devsaif.review.dto.UserDTO;
import com.devsaif.review.model.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(
            ReviewRequest reviewRequest,
            UserDTO userDTO,
            SalonDTO salonDTO
    );

    List<Review> getReviewsBySalonId(Long salonId);

    Review updateReview(
            ReviewRequest req,
            Long reviewId,
            Long userId
    ) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
}
