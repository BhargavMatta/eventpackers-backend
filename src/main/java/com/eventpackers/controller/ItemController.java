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

import java.util.List;
import java.util.Set;
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

    @PostMapping("/add")
    public Item addItem(@RequestBody ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setImageUrl(request.getImageUrl());

        if (request.getServiceIds() != null) {
            List<Service> services = serviceRepository.findAllById(request.getServiceIds());
            item.setServices(services.stream().collect(Collectors.toSet()));
        }

        return itemRepository.save(item);
    }

    @GetMapping("/all")
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::mapToItemResponse).collect(Collectors.toList());
    }

    private ItemResponse mapToItemResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setImageUrl(item.getImageUrl());

        if (item.getServices() != null) {
            List<Long> serviceIds = item.getServices().stream()
                    .map(Service::getId)
                    .collect(Collectors.toList());
            response.setServiceIds(serviceIds);
        }

        if (item.getSubItems() != null) {
            List<SubItemResponse> subItems = item.getSubItems().stream().map(subItem -> {
                SubItemResponse s = new SubItemResponse();
                s.setId(subItem.getId());
                s.setName(subItem.getName());
                s.setDescription(subItem.getDescription());
                s.setDuration(subItem.getDuration());
                s.setPrice(subItem.getPrice());
                return s;
            }).collect(Collectors.toList());

            response.setSubItems(subItems);
        }

        return response;
    }
}
