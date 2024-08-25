package org.example.servicelinkbe.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider {
    private Long id;
    private String name;
    private Address address;
    private String imageFile;
    private String description;
    private List<Offer> offers;
}
