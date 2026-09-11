package com.localfix.controller;

import com.localfix.dto.CreateReviewRequest;
import com.localfix.dto.ReviewResponse;
import com.localfix.entity.User;
import com.localfix.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ReviewResponse createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Authentication authentication
    ) {

        User customer = (User) authentication.getPrincipal();

        return reviewService.createReview(
                request,
                customer.getId()
        );
    }

    @GetMapping("/service/{serviceId}")
    public List<ReviewResponse> getServiceReviews(
            @PathVariable Long serviceId
    ) {

        return reviewService.getServiceReviews(serviceId);
    }

    @GetMapping("/service/{serviceId}/average")
    public double getAverageRating(
            @PathVariable Long serviceId
    ) {

        return reviewService.getAverageRating(serviceId);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<ReviewResponse> getMyReviews(
            Authentication authentication
    ) {

        User customer = (User) authentication.getPrincipal();

        return reviewService.getMyReviews(
                customer.getId()
        );
    }
}