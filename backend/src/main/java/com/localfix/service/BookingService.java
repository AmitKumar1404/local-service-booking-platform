package com.localfix.service;

import com.localfix.dto.BookingResponse;
import com.localfix.dto.CreateBookingRequest;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(
            CreateBookingRequest request,
            Long customerId
    );

    List<BookingResponse> getMyBookings(Long customerId);

    List<BookingResponse> getProviderBookings(Long providerId);

    BookingResponse getBookingById(
            Long bookingId,
            Long userId
    );
}