package com.localfix.controller;

import com.localfix.dto.ServiceResponse;
import com.localfix.service.LocalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final LocalServiceService localServiceService;

    @GetMapping
    public List<ServiceResponse> getAllServices() {
        return localServiceService.getAllServices();
    }

    @GetMapping("/{serviceId}")
    public ServiceResponse getServiceById(
            @PathVariable Long serviceId) {

        return localServiceService.getServiceById(serviceId);
    }
}