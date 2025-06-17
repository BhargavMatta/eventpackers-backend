package com.eventpackers.controller;

import com.eventpackers.dto.ItemRequest;
import com.eventpackers.dto.ItemResponse;
import com.eventpackers.dto.SubItemResponse;
import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.model.SubItem;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.ServiceRepository;
import com.eventpackers.repository.SubItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SubItemRepository subItemRepository;

    // ✅ 1. Create Item (with or without service linkage)
    @PostMapping("/add")
    public Item addItem(@RequestBody ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setImageUrl(request.getImageUrl());

        Set<Service> services = new HashSet<>();
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Long id : request.getServiceIds()) {
                Service service = serviceRepository.findById(id).orElse(null);
                if (service != null) {
                    services.add(service);
                }
            }
            item.setServices(services);
        }

        return itemRepository.save(item);
    }

    // ✅ 2. Get All Items (with clean SubItem + ServiceId list only)
    @GetMapping("/all")
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<Item> items = itemRepository.findAll();

        List<ItemResponse> response = items.stream().map(item -> {
            ItemResponse dto = new ItemResponse();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setImageUrl(item.getImageUrl());

            // Flattened Service ID list
            dto.setServiceIds(
                    item.getServices().stream()
                            .map(Service::getId)
                            .collect(Collectors.toList())
            );

            // SubItems (only level 1, no recursion)
            dto.setSubItems(
                    item.getSubItems().stream().map(subItem -> {
                        SubItemResponse subDto = new SubItemResponse();
                        subDto.setId(subItem.getId());
                        subDto.setName(subItem.getName());
                        subDto.setDescription(subItem.getDescription());
                        subDto.setDuration(subItem.getDuration());
                        subDto.setPrice(subItem.getPrice());
                        subDto.setItemId(item.getId());
                        return subDto;
                    }).collect(Collectors.toList())
            );

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
