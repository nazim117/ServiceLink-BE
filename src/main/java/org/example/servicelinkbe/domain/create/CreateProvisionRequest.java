package org.example.servicelinkbe.domain.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProvisionRequest {
    @NotBlank
    private String name;
    private CreateAddressRequest address;
    @NotBlank
    private String description;
}