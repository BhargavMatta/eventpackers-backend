package com.eventpackers.controller;

import com.eventpackers.model.Item;
import com.eventpackers.model.Service;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/service/{serviceId}")
    public List<Item> getItemsByService(@PathVariable Long serviceId) {
        List<Item> allItems = itemRepository.findAll();
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : allItems) {
            for (Service service : item.getServices()) {
                if (service.getId().equals(serviceId)) {
                    filteredItems.add(item);
                    break;
                }
            }
        }

        return filteredItems;
    }

    @PostMapping("/add")
    public Item addItem(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String imageUrl = (String) payload.get("imageUrl");
        List<Integer> serviceIds = (List<Integer>) payload.get("serviceIds");

        Set<Service> services = new HashSet<>();
        for (Integer id : serviceIds) {
            serviceRepository.findById(Long.valueOf(id)).ifPresent(services::add);
        }

        Item item = new Item();
        item.setName(name);
        item.setImageUrl(imageUrl);
        item.setServices(services);

        return itemRepository.save(item);
    }

    @PutMapping("/update/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Item not found");
        }

        Item item = optionalItem.get();
        item.setName((String) payload.get("name"));
        item.setImageUrl((String) payload.get("imageUrl"));

        List<Integer> serviceIds = (List<Integer>) payload.get("serviceIds");
        Set<Service> services = new HashSet<>();
        for (Integer serviceId : serviceIds) {
            serviceRepository.findById(Long.valueOf(serviceId)).ifPresent(services::add);
        }
        item.setServices(services);

        return itemRepository.save(item);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
