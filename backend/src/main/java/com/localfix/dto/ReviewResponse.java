package com.localfix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponse {

    private Long id;

    private Long bookingId;

    private Long customerId;
    private String customerName;

    private Long providerId;
    private String providerName;

    private Long serviceId;
    private String serviceTitle;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}