package org.example.servicelinkbe.domain.create;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceProviderRequest {
    @NotBlank
    private String name;
    private CreateAddressRequest address;
    @NotBlank
    private String imagePath;
    @NotBlank
    private String description;
}
