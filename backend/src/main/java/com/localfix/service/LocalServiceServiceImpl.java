package com.localfix.service;

import com.localfix.dto.CreateServiceRequest;
import com.localfix.dto.ServiceResponse;
import com.localfix.dto.UpdateServiceRequest;
import com.localfix.entity.Service;
import com.localfix.entity.User;
import com.localfix.repository.ServiceRepository;
import com.localfix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class LocalServiceServiceImpl implements LocalServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    @Override
    public ServiceResponse createService(
            CreateServiceRequest request,
            Long providerId) {

        User provider = userRepository.findById(providerId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Provider not found"
                        ));

        Service service = Service.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .provider(provider)
                .build();

        Service savedService = serviceRepository.save(service);

        return mapToResponse(savedService);
    }

    @Override
    public List<ServiceResponse> getMyServices(Long providerId) {

        User provider = userRepository.findById(providerId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Provider not found"
                        ));

        return serviceRepository.findByProvider(provider)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ServiceResponse> getAllServices() {

        return serviceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ServiceResponse getServiceById(Long serviceId) {

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Service not found"
                        ));

        return mapToResponse(service);
    }

    private ServiceResponse mapToResponse(Service service) {

        return ServiceResponse.builder()
                .id(service.getId())
                .title(service.getTitle())
                .description(service.getDescription())
                .category(service.getCategory())
                .price(service.getPrice())
                .providerId(service.getProvider().getId())
                .providerName(service.getProvider().getName())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    @Override
    public ServiceResponse updateService(
            Long serviceId,
            UpdateServiceRequest request,
            Long providerId) {

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Service not found"
                        ));

        if (!service.getProvider().getId().equals(providerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to update this service"
            );
        }

        service.setTitle(request.getTitle());
        service.setDescription(request.getDescription());
        service.setCategory(request.getCategory());
        service.setPrice(request.getPrice());

        Service updatedService = serviceRepository.save(service);

        return mapToResponse(updatedService);
    }

    @Override
    public void deleteService(
            Long serviceId,
            Long providerId) {

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Service not found"
                        ));
        if (!service.getProvider().getId().equals(providerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this service"
            );
        }

        serviceRepository.delete(service);
    }
}