package org.example.servicelinkbe.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.interfaces.*;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateServiceProviderRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://servicelink-load-balancer-1683365512.eu-central-1.elb.amazonaws.com")
@RestController
@RequestMapping("/api/services")
@AllArgsConstructor
public class ServiceProviderController {
    private final GetServiceProvidersUseCase getServiceProvidersUseCase;
    private final GetSingleServiceProviderUseCase getSingleServiceProviderUseCase;
    private final CreateServiceProviderUseCase createServiceProviderUseCase;
    private final UpdateServiceProviderUseCase updateServiceProviderUseCase;
    private final DeleteServiceProviderUseCase deleteServiceProviderUseCase;

    @GetMapping
    public ResponseEntity<GetAllProvisionsResponse> getProvisions(){
        return ResponseEntity.ok(getServiceProvidersUseCase.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<ServiceProvider> getProvision(@PathVariable(value = "id") final Long id){
        ServiceProvider serviceProvider = null;
        try {
            serviceProvider = getSingleServiceProviderUseCase.get(id);
            if(serviceProvider == null){
                return  ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(serviceProvider);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createProvision(@Valid @RequestBody CreateServiceProviderRequest request){
        CreateResponse response = createServiceProviderUseCase.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProvision(@PathVariable(value = "id") final Long id,
                                           @RequestBody @Valid UpdateProvisionRequest request){
        request.setId(id);
        try {
            updateServiceProviderUseCase.update(request);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProvision(@PathVariable(value = "id") final Long id){
        try {
            deleteServiceProviderUseCase.delete(id);
            return ResponseEntity.noContent().build();

        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
