package com.eventpackers.controller;

import com.eventpackers.dto.ServiceResponse;
import com.eventpackers.dto.ItemResponse;
import com.eventpackers.model.Service;
import com.eventpackers.model.Item;
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

            List<ItemResponse> itemResponses = service.getItems().stream().map(item -> {
                ItemResponse itemDto = new ItemResponse();
                itemDto.setId(item.getId());
                itemDto.setName(item.getName());
                itemDto.setImageUrl(item.getImageUrl());
                return itemDto;
            }).collect(Collectors.toList());

            dto.setItems(itemResponses);
            return dto;
        }).collect(Collectors.toList());
    }
}
