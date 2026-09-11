package com.localfix.service;

import com.localfix.dto.CreateReviewRequest;
import com.localfix.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest request, Long customerId);

    List<ReviewResponse> getServiceReviews(Long serviceId);

    double getAverageRating(Long serviceId);

    List<ReviewResponse> getMyReviews(Long customerId);
}