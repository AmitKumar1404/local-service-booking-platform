package com.localfix.controller;

import com.localfix.dto.UpdateServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.localfix.dto.CreateServiceRequest;
import com.localfix.dto.ServiceResponse;
import com.localfix.service.LocalServiceService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import com.localfix.entity.User;
import com.localfix.dto.BookingResponse;
import com.localfix.service.BookingService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final LocalServiceService localServiceService;

    private final BookingService bookingService;

    @GetMapping("/test")
    @PreAuthorize("hasRole('PROVIDER')")
    public String providerTest() {
        return "Provider API is working!";
    }

    @PostMapping("/services")
    @PreAuthorize("hasRole('PROVIDER')")
    public ServiceResponse createService(
            @Valid @RequestBody CreateServiceRequest request,
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return localServiceService.createService(
                request,
                provider.getId()
        );
    }

    @GetMapping("/services/my")
    @PreAuthorize("hasRole('PROVIDER')")
    public List<ServiceResponse> getMyServices(
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return localServiceService.getMyServices(
                provider.getId()
        );
    }

    @PutMapping("/services/{serviceId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ServiceResponse updateService(
            @PathVariable Long serviceId,
            @Valid @RequestBody UpdateServiceRequest request,
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return localServiceService.updateService(
                serviceId,
                request,
                provider.getId()
        );
    }

    @DeleteMapping("/services/{serviceId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long serviceId,
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        localServiceService.deleteService(
                serviceId,
                provider.getId()
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasRole('PROVIDER')")
    public List<BookingResponse> getProviderBookings(
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return bookingService.getProviderBookings(
                provider.getId()
        );
    }

    @PutMapping("/bookings/{bookingId}/accept")
    @PreAuthorize("hasRole('PROVIDER')")
    public BookingResponse acceptBooking(
            @PathVariable Long bookingId,
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return bookingService.acceptBooking(
                bookingId,
                provider.getId()
        );
    }

    @PutMapping("/bookings/{bookingId}/reject")
    @PreAuthorize("hasRole('PROVIDER')")
    public BookingResponse rejectBooking(
            @PathVariable Long bookingId,
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return bookingService.rejectBooking(
                bookingId,
                provider.getId()
        );
    }

    @PutMapping("/bookings/{bookingId}/complete")
    @PreAuthorize("hasRole('PROVIDER')")
    public BookingResponse completeBooking(
            @PathVariable Long bookingId,
            Authentication authentication) {

        User provider = (User) authentication.getPrincipal();

        return bookingService.completeBooking(
                bookingId,
                provider.getId()
        );
    }


}