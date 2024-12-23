package org.example.servicelinkbe.domain.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProvisionRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private CreateAddressRequest address;
    @NotBlank
    private String description;
}
