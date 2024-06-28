package org.example.servicelinkbe.domain.update;

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
public class UpdateAppointmentRequest {
    @NotNull
    private Long id;
    @NotPastOrEmpty
    private LocalDateTime datetime;
    @NotBlank
    private String description;
}
