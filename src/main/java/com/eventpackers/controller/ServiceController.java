package com.eventpackers.controller;

import com.eventpackers.dto.ServiceResponse;
import com.eventpackers.dto.ItemResponse;
import com.eventpackers.dto.SubItemResponse;
import com.eventpackers.model.Service;
import com.eventpackers.model.Item;
import com.eventpackers.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@CrossOrigin
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

            List<ItemResponse> itemResponses = service.getItems().stream().map(item -> {
                ItemResponse itemResponse = new ItemResponse();
                itemResponse.setId(item.getId());
                itemResponse.setName(item.getName());
                itemResponse.setImageUrl(item.getImageUrl());

                List<SubItemResponse> subItemResponses = item.getSubItems().stream().map(subItem -> {
                    SubItemResponse subResponse = new SubItemResponse();
                    subResponse.setId(subItem.getId());
                    subResponse.setName(subItem.getName());
                    subResponse.setDescription(subItem.getDescription());
                    subResponse.setDuration(String.valueOf(subItem.getDuration()));
                    subResponse.setPrice(subItem.getPrice());
                    return subResponse;
                }).collect(Collectors.toList());

                itemResponse.setSubItems(subItemResponses);
                return itemResponse;
            }).collect(Collectors.toList());

            response.setItems(itemResponses);
            return response;
        }).collect(Collectors.toList());
    }
}
