package org.example.servicelinkbe.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.service_provider_service.exceptions.ServiceProviderNotFoundException;
import org.example.servicelinkbe.business.service_provider_service.interfaces.*;
import org.example.servicelinkbe.domain.create.CreateAddressRequest;
import org.example.servicelinkbe.domain.get.GetAllProvisionsResponse;
import org.example.servicelinkbe.domain.update.UpdateProvisionRequest;
import org.example.servicelinkbe.domain.create.CreateServiceProviderRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.ServiceProvider;
import org.example.servicelinkbe.utilities.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/services")
@AllArgsConstructor
public class ServiceProviderController {
    private final GetServiceProvidersUseCase getServiceProvidersUseCase;
    private final GetSingleServiceProviderUseCase getSingleServiceProviderUseCase;
    private final CreateServiceProviderUseCase createServiceProviderUseCase;
    private final UpdateServiceProviderUseCase updateServiceProviderUseCase;
    private final DeleteServiceProviderUseCase deleteServiceProviderUseCase;
    private final FileStorageService fileStorageService;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().body(serviceProvider);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ServiceProvider> getProvisionByUserId(@PathVariable(value = "userId") final Long userId){
        ServiceProvider serviceProvider = null;
        try {
            serviceProvider = getSingleServiceProviderUseCase.getByUserId(userId);
        } catch (ServiceProviderNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().body(serviceProvider);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createProvision(@RequestParam("name") String name,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("userId") String userIdStr,
                                                          @RequestParam("imageFile") MultipartFile imageFile,
                                                          @RequestParam("street") String street,
                                                          @RequestParam("city") String city,
                                                          @RequestParam("postalCode") String postalCode,
                                                          @RequestParam("country") String country){
        try{
            System.out.println("Received file: " + imageFile.getOriginalFilename());
            Long userId = Long.parseLong(userIdStr);
            String imagePath = fileStorageService.saveImage(imageFile);

            CreateAddressRequest address = CreateAddressRequest.builder()
                    .street(street)
                    .city(city)
                    .postalCode(postalCode)
                    .country(country)
                    .build();

            CreateServiceProviderRequest request = CreateServiceProviderRequest.builder()
                    .name(name)
                    .description(description)
                    .userId(userId)
                    .imagePath(imagePath)
                    .address(address)
                    .build();

            CreateResponse response = createServiceProviderUseCase.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (ServiceProviderNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProvision(@PathVariable(value = "id") final Long id,
                                           @RequestBody @Valid UpdateProvisionRequest request){
        request.setId(id);
        try {
            updateServiceProviderUseCase.update(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
