package com.localfix.dto;

import com.localfix.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    private Long serviceId;
    private String serviceTitle;
    private BigDecimal servicePrice;

    private Long customerId;
    private String customerName;

    private Long providerId;
    private String providerName;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    private BookingStatus status;

    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}