package com.localfix.repository;

import com.localfix.entity.Review;
import com.localfix.entity.Service;
import com.localfix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByBookingId(Long bookingId);

    List<Review> findByService(Service service);

    List<Review> findByCustomer(User customer);

    boolean existsByBookingId(Long bookingId);
}