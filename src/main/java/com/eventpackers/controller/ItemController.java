package com.eventpackers.controller;

import com.eventpackers.dto.ItemRequest;
import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // ✅ Create Item (with or without service linkage)
    @PostMapping("/add")
    public Item addItem(@RequestBody ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setImageUrl(request.getImageUrl());

        Set<Service> services = new HashSet<>();
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Long id : request.getServiceIds()) {
                Service service = serviceRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Service not found with ID: " + id));
                services.add(service);
            }
        }

        item.setServices(services);
        return itemRepository.save(item);
    }

    // ✅ Get all items
    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ✅ Get item by ID
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    // ✅ Update item by ID
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody ItemRequest request) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Item not found with ID: " + id);
        }

        Item item = optionalItem.get();
        item.setName(request.getName());
        item.setImageUrl(request.getImageUrl());

        Set<Service> services = new HashSet<>();
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Long sid : request.getServiceIds()) {
                Service service = serviceRepository.findById(sid)
                        .orElseThrow(() -> new RuntimeException("Service not found with ID: " + sid));
                services.add(service);
            }
        }

        item.setServices(services);
        return itemRepository.save(item);
    }

    // ✅ Delete item by ID
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
