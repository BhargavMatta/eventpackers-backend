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
                Service service = serviceRepository.findById(id).orElse(null);
                if (service != null) {
                    services.add(service);
                }
            }
            item.setServices(services);
        }

        return itemRepository.save(item);
    }

    @GetMapping("/all")
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();

        return items.stream().map(item -> {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setName(item.getName());
            itemResponse.setImageUrl(item.getImageUrl());

            List<SubItemResponse> subItems = item.getSubItems().stream().map(subItem -> {
                SubItemResponse subItemResponse = new SubItemResponse();
                subItemResponse.setId(subItem.getId());
                subItemResponse.setName(subItem.getName());
                subItemResponse.setDescription(subItem.getDescription());
                subItemResponse.setDuration(subItem.getDuration());
                subItemResponse.setPrice(subItem.getPrice());
                return subItemResponse;
            }).collect(Collectors.toList());

            itemResponse.setSubItems(subItems);
            return itemResponse;
        }).collect(Collectors.toList());
    }
}
