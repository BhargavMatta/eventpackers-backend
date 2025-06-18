package com.eventpackers.controller;

import com.eventpackers.dto.ItemRequest;
import com.eventpackers.dto.ItemResponse;
import com.eventpackers.dto.SubItemResponse;
import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.model.SubItem;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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

    @PostMapping("/add")
    public Item addItem(@RequestBody ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setImageUrl(request.getImageUrl());

        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            Set<Service> services = new HashSet<>();
            for (Long id : request.getServiceIds()) {
                serviceRepository.findById(id).ifPresent(services::add);
            }
            item.setServices(services);
        }

        return itemRepository.save(item);
    }

    @GetMapping("/all")
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream().map(item -> {
            ItemResponse response = new ItemResponse();
            response.setId(item.getId());
            response.setName(item.getName());
            response.setImageUrl(item.getImageUrl());

            List<SubItemResponse> subItems = item.getSubItems().stream().map(sub -> {
                SubItemResponse subRes = new SubItemResponse();
                subRes.setId(sub.getId());
                subRes.setName(sub.getName());
                subRes.setDescription(sub.getDescription());
                subRes.setDuration(sub.getDuration());
                subRes.setPrice(sub.getPrice());
                return subRes;
            }).collect(Collectors.toList());

            response.setSubItems(subItems);
            return response;
        }).collect(Collectors.toList());
    }
}
