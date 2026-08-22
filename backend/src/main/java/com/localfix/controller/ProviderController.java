package com.localfix.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('PROVIDER')")
    public String providerTest() {
        return "Provider API is working!";
    }
}