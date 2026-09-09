package com.localfix.controller;

import com.localfix.dto.BookingResponse;
import com.localfix.dto.CreateBookingRequest;
import com.localfix.entity.User;
import com.localfix.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public BookingResponse createBooking(
            @Valid @RequestBody CreateBookingRequest request,
            Authentication authentication) {

        User customer = (User) authentication.getPrincipal();

        return bookingService.createBooking(
                request,
                customer.getId()
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<BookingResponse> getMyBookings(
            Authentication authentication) {

        User customer = (User) authentication.getPrincipal();

        return bookingService.getMyBookings(
                customer.getId()
        );
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBookingById(
            @PathVariable Long bookingId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return bookingService.getBookingById(
                bookingId,
                user.getId()
        );
    }

    @PutMapping("/{bookingId}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public BookingResponse cancelBooking(
            @PathVariable Long bookingId,
            Authentication authentication) {

        User customer = (User) authentication.getPrincipal();

        return bookingService.cancelBooking(
                bookingId,
                customer.getId()
        );
    }
}