package org.example.servicelinkbe.domain.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.Appointment;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAppointmentsResponse {
    List<Appointment> appointments;
}
