package com.example.netops.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Optional<Device> findByIp(String ip);
}
