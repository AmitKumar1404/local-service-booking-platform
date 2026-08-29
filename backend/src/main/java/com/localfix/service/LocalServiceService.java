package com.localfix.service;

import com.localfix.dto.CreateServiceRequest;
import com.localfix.dto.ServiceResponse;
import com.localfix.dto.UpdateServiceRequest;

import java.util.List;

public interface LocalServiceService {

    ServiceResponse createService(CreateServiceRequest request, Long providerId);

    List<ServiceResponse> getMyServices(Long providerId);
    List<ServiceResponse> getAllServices();
    ServiceResponse getServiceById(Long serviceId);
    ServiceResponse updateService(
            Long serviceId,
            UpdateServiceRequest request,
            Long providerId
    );

    void deleteService(
            Long serviceId,
            Long providerId
    );
}
