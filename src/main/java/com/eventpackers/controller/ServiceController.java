package com.eventpackers.controller;

import com.eventpackers.model.Service;
import com.eventpackers.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/add")
    public Service addService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @GetMapping("/all")
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            Service service = optionalService.get();
            service.setName(updatedService.getName());
            service.setDescription(updatedService.getDescription());
            service.setImageUrl(updatedService.getImageUrl());
            return serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
    }
}
