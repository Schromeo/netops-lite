package com.example.netops.device;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device",
        indexes = { @Index(name = "idx_device_name", columnList = "name") },
        uniqueConstraints = { @UniqueConstraint(name = "uk_device_ip", columnNames = "ip") }
)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 15)
    private String ip;

    @Column(length = 64)
    private String model;

    @Column(length = 32)
    private String status;

    private LocalDateTime lastSeen;

    @PrePersist
    public void prePersist() {
        if (lastSeen == null) lastSeen = LocalDateTime.now();
    }

    // getters/setters（IntelliJ 可自动生成；为简洁此处略）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getLastSeen() { return lastSeen; }
    public void setLastSeen(LocalDateTime lastSeen) { this.lastSeen = lastSeen; }
}
