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

    @GetMapping("/all")
    public List<ServiceResponse> getAllServices() {
        List<Service> services = serviceRepository.findAll();

        return services.stream().map(service -> {
            ServiceResponse response = new ServiceResponse();
            response.setId(service.getId());
            response.setName(service.getName());
            response.setDescription(service.getDescription());
            response.setImageUrl(service.getImageUrl());

            List<ItemResponse> itemResponses = service.getItems().stream().map(item -> {
                ItemResponse itemResponse = new ItemResponse();
                itemResponse.setId(item.getId());
                itemResponse.setName(item.getName());
                itemResponse.setImageUrl(item.getImageUrl());
                // Do NOT include service or nested items
                return itemResponse;
            }).collect(Collectors.toList());

            response.setItems(itemResponses);
            return response;
        }).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public Service addService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }
}
