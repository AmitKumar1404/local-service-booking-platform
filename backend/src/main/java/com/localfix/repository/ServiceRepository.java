package com.localfix.repository;

import com.localfix.entity.Service;
import com.localfix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByProvider(User provider);
}