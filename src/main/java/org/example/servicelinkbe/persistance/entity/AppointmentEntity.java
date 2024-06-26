package org.example.servicelinkbe.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name="appointments")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "date")
    private LocalDateTime datetime;

    @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserEntity user;
}
