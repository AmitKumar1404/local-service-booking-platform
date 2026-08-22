package com.localfix.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customerTest() {
        return "Customer API is working!";
    }
}