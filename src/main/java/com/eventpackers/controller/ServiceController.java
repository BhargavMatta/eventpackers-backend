package com.eventpackers.controller;

import com.eventpackers.dto.ItemResponse;
import com.eventpackers.dto.ServiceResponse;
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
            ServiceResponse serviceDto = new ServiceResponse();
            serviceDto.setId(service.getId());
            serviceDto.setName(service.getName());
            serviceDto.setDescription(service.getDescription());
            serviceDto.setImageUrl(service.getImageUrl());

            List<ItemResponse> itemDtos = service.getItems().stream().map(item -> {
                ItemResponse dto = new ItemResponse();
                dto.setId(item.getId());
                dto.setName(item.getName());
                dto.setImageUrl(item.getImageUrl());
                return dto;
            }).collect(Collectors.toList());

            serviceDto.setItems(itemDtos);
            return serviceDto;
        }).collect(Collectors.toList());
    }
}
