package com.eventpackers.controller;

import com.eventpackers.dto.ServiceResponse;
import com.eventpackers.dto.ItemResponse;
import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.repository.ServiceRepository;
import com.eventpackers.repository.ItemRepository;

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

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/add")
    public Service addService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @GetMapping("/all")
    public List<ServiceResponse> getAllServices() {
        List<Service> services = serviceRepository.findAll();

        return services.stream().map(service -> {
            ServiceResponse response = new ServiceResponse();
            response.setId(service.getId());
            response.setName(service.getName());
            response.setDescription(service.getDescription());
            response.setImageUrl(service.getImageUrl());

            // Convert related items to DTOs without circular nesting
            List<ItemResponse> itemDTOs = service.getItems().stream().map(item -> {
                ItemResponse itemDTO = new ItemResponse();
                itemDTO.setId(item.getId());
                itemDTO.setName(item.getName());
                itemDTO.setImageUrl(item.getImageUrl());
                return itemDTO;
            }).collect(Collectors.toList());

            response.setItems(itemDTOs);
            return response;
        }).collect(Collectors.toList());
    }
}
