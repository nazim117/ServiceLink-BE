package org.example.servicelinkbe.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.provision_service.interfaces.*;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.get.GetAllUsersResponse;
import org.example.servicelinkbe.domain.Provision;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@AllArgsConstructor
public class ProvisionController {
    private final GetProvisionsUseCase getProvisionsUseCase;
    private final GetSingleProvisionUseCase getSingleProvisionUseCase;
    private final CreateProvisionUseCase createProvisionUseCase;
    private final UpdateProvisionUseCase updateProvisionUseCase;
    private final DeleteProvisionUseCase deleteProvisionUseCase;

    @GetMapping
    public ResponseEntity<GetAllProvisionsResponse> getProvisions(){
        return ResponseEntity.ok(getProvisionsUseCase.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<Provision> getProvision(@PathVariable(value = "id") final Long id){
        Provision provision = null;
        try {
            provision = getSingleProvisionUseCase.get(id);
            if(provision == null){
                return  ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(provision);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createProvision(@Valid @RequestBody CreateProvisionRequest request){
        CreateResponse response = createProvisionUseCase.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProvision(@PathVariable(value = "id") final Long id,
                                           @RequestBody @Valid UpdateProvisionRequest request){
        request.setId(id);
        try {
            updateProvisionUseCase.update(request);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProvision(@PathVariable(value = "id") final Long id){
        try {
            deleteProvisionUseCase.delete(id);
            return ResponseEntity.noContent().build();

        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
