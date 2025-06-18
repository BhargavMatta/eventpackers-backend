package com.eventpackers.controller;

import com.eventpackers.dto.ItemResponse;
import com.eventpackers.dto.ServiceResponse;
import com.eventpackers.dto.SubItemResponse;
import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.model.SubItem;
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

                // Map sub-items to SubItemResponse to avoid circular reference
                List<SubItemResponse> subItemResponses = item.getSubItems().stream().map(subItem -> {
                    SubItemResponse subDto = new SubItemResponse();
                    subDto.setId(subItem.getId());
                    subDto.setName(subItem.getName());
                    subDto.setDescription(subItem.getDescription());
                    subDto.setDuration(subItem.getDuration());
                    subDto.setPrice(subItem.getPrice());
                    return subDto;
                }).collect(Collectors.toList());

                itemResponse.setSubItems(subItemResponses);

                // Set associated service IDs
                itemResponse.setServiceIds(item.getServices().stream()
                        .map(Service::getId)
                        .collect(Collectors.toList()));

                return itemResponse;
            }).collect(Collectors.toList());

            response.setItems(itemResponses);
            return response;
        }).collect(Collectors.toList());
    }
}
