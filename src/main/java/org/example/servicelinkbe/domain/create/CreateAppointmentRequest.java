package org.example.servicelinkbe.domain.create;

import jakarta.validation.constraints.NotBlank;
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
    @NotPastOrEmpty
    private LocalDateTime datetime;
    @NotBlank
    private String description;
}
