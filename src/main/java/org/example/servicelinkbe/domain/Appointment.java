package org.example.servicelinkbe.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.servicelinkbe.persistance.entity.OfferEntity;
import org.example.servicelinkbe.persistance.entity.ServiceProviderEntity;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String clientName;

    private String clientEmail;

    private ServiceProviderEntity service;

    private OfferEntity offer;

    private UserEntity user;
}