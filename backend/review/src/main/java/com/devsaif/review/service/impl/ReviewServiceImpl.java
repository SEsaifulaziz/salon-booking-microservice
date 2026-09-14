package com.devsaif.review.service.impl;

import com.devsaif.review.dto.ReviewRequest;
import com.devsaif.review.dto.SalonDTO;
import com.devsaif.review.dto.UserDTO;
import com.devsaif.review.model.Review;
import com.devsaif.review.repository.ReviewRepository;
import com.devsaif.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest req, UserDTO userDTO, SalonDTO salonDTO) {

        Review review = new Review();

        review.setReviewText(req.getReviewerText());
        review.setRating(req.getRating());
        review.setUserId(userDTO.getId());
        review.setSalonId(salonDTO.getId());

        return reviewRepository.save(review);
    }



    @Override
    public List<Review> getReviewsBySalonId(Long salonId) {

        return reviewRepository.findBySalonId(salonId);

    }

    @Override
    public Review updateReview(ReviewRequest req, Long reviewId, Long userId) throws Exception {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("review does not exist"));

        if(!review.getUserId().equals(userId)){
            throw new Exception("you don't have permission to update review");
        }
        review.setReviewText(req.getReviewerText());
        review.setRating(req.getRating());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("review does not exist"));

        if(!review.getUserId().equals(userId)){
            throw new Exception("you don't have permission to update review");
        }

        reviewRepository.delete(review);
    }
}
