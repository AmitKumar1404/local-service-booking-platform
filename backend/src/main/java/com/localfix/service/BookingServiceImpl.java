package com.localfix.service;

import com.localfix.dto.BookingResponse;
import com.localfix.dto.CreateBookingRequest;
import com.localfix.entity.Booking;
import com.localfix.entity.BookingStatus;
import com.localfix.entity.Service;
import com.localfix.entity.User;
import com.localfix.repository.BookingRepository;
import com.localfix.repository.ServiceRepository;
import com.localfix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponse createBooking(
            CreateBookingRequest request,
            Long customerId) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Service service = serviceRepository.findById(
                request.getServiceId()
        ).orElseThrow(() ->
                new RuntimeException("Service not found"));

        Booking booking = Booking.builder()
                .service(service)
                .customer(customer)
                .provider(service.getProvider())
                .bookingDate(request.getBookingDate())
                .bookingTime(request.getBookingTime())
                .status(BookingStatus.PENDING)
                .notes(request.getNotes())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public List<BookingResponse> getMyBookings(Long customerId) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return bookingRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getProviderBookings(Long providerId) {

        User provider = userRepository.findById(providerId)
                .orElseThrow(() ->
                        new RuntimeException("Provider not found"));

        return bookingRepository.findByProvider(provider)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookingResponse getBookingById(
            Long bookingId,
            Long userId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        boolean isCustomer =
                booking.getCustomer().getId().equals(userId);

        boolean isProvider =
                booking.getProvider().getId().equals(userId);

        if (!isCustomer && !isProvider) {
            throw new RuntimeException(
                    "You are not allowed to view this booking"
            );
        }

        return mapToResponse(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())

                .serviceId(booking.getService().getId())
                .serviceTitle(booking.getService().getTitle())
                .servicePrice(booking.getService().getPrice())

                .customerId(booking.getCustomer().getId())
                .customerName(booking.getCustomer().getName())

                .providerId(booking.getProvider().getId())
                .providerName(booking.getProvider().getName())

                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())

                .status(booking.getStatus())

                .notes(booking.getNotes())

                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())

                .build();
    }

    @Override
    public BookingResponse acceptBooking(Long bookingId, Long providerId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found")
                );

        if (!booking.getProvider().getId().equals(providerId)) {
            throw new RuntimeException(
                    "You are not allowed to manage this booking"
            );
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending bookings can be accepted"
            );
        }

        booking.setStatus(BookingStatus.ACCEPTED);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponse rejectBooking(Long bookingId, Long providerId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found")
                );

        if (!booking.getProvider().getId().equals(providerId)) {
            throw new RuntimeException(
                    "You are not allowed to manage this booking"
            );
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending bookings can be rejected"
            );
        }

        booking.setStatus(BookingStatus.REJECTED);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponse completeBooking(
            Long bookingId,
            Long providerId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found")
                );

        if (!booking.getProvider().getId().equals(providerId)) {
            throw new RuntimeException(
                    "You are not allowed to manage this booking"
            );
        }

        if (booking.getStatus() != BookingStatus.ACCEPTED) {
            throw new RuntimeException(
                    "Only accepted bookings can be completed"
            );
        }

        booking.setStatus(BookingStatus.COMPLETED);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponse cancelBooking(
            Long bookingId,
            Long customerId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found")
                );

        if (!booking.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException(
                    "You are not allowed to cancel this booking"
            );
        }

        if (booking.getStatus() != BookingStatus.PENDING
                && booking.getStatus() != BookingStatus.ACCEPTED) {

            throw new RuntimeException(
                    "This booking cannot be cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }
}