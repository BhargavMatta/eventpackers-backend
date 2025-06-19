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

    @GetMapping("/all")
    public List<ServiceResponse> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        return services.stream()
                .map(this::mapToServiceResponse)
                .collect(Collectors.toList());
    }

    private ServiceResponse mapToServiceResponse(Service service) {
        ServiceResponse response = new ServiceResponse();
        response.setId(service.getId());
        response.setName(service.getName());
        response.setDescription(service.getDescription());
        response.setImageUrl(service.getImageUrl());

        if (service.getItems() != null) {
            List<ItemResponse> itemResponses = service.getItems().stream().map(item -> {
                ItemResponse itemResponse = new ItemResponse();
                itemResponse.setId(item.getId());
                itemResponse.setName(item.getName());
                itemResponse.setImageUrl(item.getImageUrl());

                if (item.getSubItems() != null) {
                    List<SubItemResponse> subItemResponses = item.getSubItems().stream().map(subItem -> {
                        SubItemResponse sub = new SubItemResponse();
                        sub.setId(subItem.getId());
                        sub.setName(subItem.getName());
                        sub.setDescription(subItem.getDescription());
                        sub.setDuration(subItem.getDuration());
                        sub.setPrice(subItem.getPrice());
                        return sub;
                    }).collect(Collectors.toList());
                    itemResponse.setSubItems(subItemResponses);
                }

                return itemResponse;
            }).collect(Collectors.toList());

            response.setItems(itemResponses);
        }

        return response;
    }
}
