package org.example.servicelinkbe.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.appointment_service.interfaces.*;
import org.example.servicelinkbe.domain.Appointment;
import org.example.servicelinkbe.domain.create.CreateAppointmentRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.get.GetAllAppointmentsResponse;
import org.example.servicelinkbe.domain.update.UpdateAppointmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
public class AppointmentController {
    private final GetAppointmentsUseCase getAppointmentsUseCase;
    private final GetSingleAppointmentUseCase getSingleAppointmentUseCase;
    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final DeleteAppointmentUseCase deleteAppointmentUseCase;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;

    @GetMapping("/serviceId/{serviceId}")
    public ResponseEntity<GetAllAppointmentsResponse> getAppointments(@PathVariable(value = "serviceId") final Long serviceId) throws EntityNotFoundException{
        return ResponseEntity.ok(getAppointmentsUseCase.get(serviceId));
    }

    @GetMapping("{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable(value = "id") final Long id){
        Appointment appointment = null;
        try {
            appointment = getSingleAppointmentUseCase.get(id);
            if(appointment == null){
                return  ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(appointment);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@Valid @RequestBody CreateAppointmentRequest request) {
        try{
            CreateResponse response = createAppointmentUseCase.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (EntityExistsException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable(value = "id") final Long id,
                                                @RequestBody @Valid UpdateAppointmentRequest request){
        request.setId(id);
        try {
            updateAppointmentUseCase.update(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") final Long id){
        try {
            deleteAppointmentUseCase.delete(id);
            return ResponseEntity.noContent().build();

        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
