package com.localfix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ServiceResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;

    private Long providerId;
    private String providerName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}