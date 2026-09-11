package com.localfix.service;

import com.localfix.dto.CreateReviewRequest;
import com.localfix.dto.ReviewResponse;
import com.localfix.entity.Booking;
import com.localfix.entity.BookingStatus;
import com.localfix.entity.Review;
//import com.localfix.entity.Service;
import com.localfix.entity.User;
import com.localfix.repository.BookingRepository;
import com.localfix.repository.ReviewRepository;
import com.localfix.repository.ServiceRepository;
import com.localfix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public ReviewResponse createReview(
            CreateReviewRequest request,
            Long customerId
    ) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        // Check booking ownership
        if (!booking.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException(
                    "You are not allowed to review this booking"
            );
        }

        // Review only after completion
        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new RuntimeException(
                    "Review can only be added for completed bookings"
            );
        }

        // One review per booking
        if (reviewRepository.existsByBookingId(booking.getId())) {
            throw new RuntimeException(
                    "Review already exists for this booking"
            );
        }

        Review review = Review.builder()
                .booking(booking)
                .customer(customer)
                .provider(booking.getProvider())
                .service(booking.getService())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        Review savedReview = reviewRepository.save(review);

        return mapToResponse(savedReview);
    }

    @Override
    public List<ReviewResponse> getServiceReviews(Long serviceId) {

        com.localfix.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException("Service not found"));

        return reviewRepository.findByService(service)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public double getAverageRating(Long serviceId) {

        com.localfix.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException("Service not found"));

        List<Review> reviews = reviewRepository.findByService(service);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<ReviewResponse> getMyReviews(Long customerId) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return reviewRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReviewResponse mapToResponse(Review review) {

        return ReviewResponse.builder()
                .id(review.getId())
                .bookingId(review.getBooking().getId())
                .customerId(review.getCustomer().getId())
                .customerName(review.getCustomer().getName())
                .providerId(review.getProvider().getId())
                .providerName(review.getProvider().getName())
                .serviceId(review.getService().getId())
                .serviceTitle(review.getService().getTitle())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}