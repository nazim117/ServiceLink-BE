package org.example.servicelinkbe.domain.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.servicelinkbe.utilities.NotPastOrEmpty;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotNull
    private Long serviceId;
    @NotNull
    private Long offerId;
    @NotBlank
    private String clientName;
    @NotBlank
    private String clientEmail;

    private String description;
}
