package com.eventpackers.controller;

import com.eventpackers.dto.ServiceResponse;
import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/add")
    public Service addService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @GetMapping("/all")
    public List<ServiceResponse> getAllServices() {
        List<Service> services = serviceRepository.findAll();

        return services.stream().map(service -> {
            ServiceResponse dto = new ServiceResponse();
            dto.setId(service.getId());
            dto.setName(service.getName());
            dto.setDescription(service.getDescription());
            dto.setImageUrl(service.getImageUrl());

            List<ServiceResponse.ItemSimpleDTO> itemDTOs = service.getItems().stream().map(item -> {
                ServiceResponse.ItemSimpleDTO itemDTO = new ServiceResponse.ItemSimpleDTO();
                itemDTO.setId(item.getId());
                itemDTO.setName(item.getName());
                itemDTO.setImageUrl(item.getImageUrl());
                return itemDTO;
            }).collect(Collectors.toList());

            dto.setItems(itemDTOs);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Service getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        return serviceRepository.findById(id).map(service -> {
            service.setName(updatedService.getName());
            service.setDescription(updatedService.getDescription());
            service.setImageUrl(updatedService.getImageUrl());
            return serviceRepository.save(service);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
    }
}
