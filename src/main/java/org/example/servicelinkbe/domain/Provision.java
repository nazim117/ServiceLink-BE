package org.example.servicelinkbe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Provision {
    private Long id;
    private String name;
    private Address address;
    private String description;
}
