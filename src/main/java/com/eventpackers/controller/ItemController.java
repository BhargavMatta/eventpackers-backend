package com.eventpackers.controller;

import com.eventpackers.dto.ItemResponse;
import com.eventpackers.dto.SubItemResponse;
import com.eventpackers.model.Item;
import com.eventpackers.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/all")
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::mapToItemResponse).collect(Collectors.toList());
    }

    private ItemResponse mapToItemResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());

        if (item.getSubItems() != null) {
            List<SubItemResponse> subItems = item.getSubItems().stream().map(subItem -> {
                SubItemResponse s = new SubItemResponse();
                s.setId(subItem.getId());
                s.setName(subItem.getName());
                s.setDescription(subItem.getDescription());
                s.setDuration(subItem.getDuration());  // ✅ duration is String now
                s.setPrice(subItem.getPrice());
                return s;
            }).collect(Collectors.toList());
            response.setSubItems(subItems);
        }

        return response;
    }
}
