package com.eventpackers.controller;

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
import java.util.HashSet;
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

    @GetMapping("/all")
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(item -> {
            ItemResponse dto = new ItemResponse();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setImageUrl(item.getImageUrl());

            dto.setServiceIds(item.getServices().stream().map(Service::getId).collect(Collectors.toList()));

            List<SubItemResponse> subItemResponses = item.getSubItems().stream().map(sub -> {
                SubItemResponse subDto = new SubItemResponse();
                subDto.setId(sub.getId());
                subDto.setName(sub.getName());
                subDto.setDescription(sub.getDescription());
                subDto.setPrice(sub.getPrice());
                subDto.setDuration(sub.getDuration());
                return subDto;
            }).collect(Collectors.toList());

            dto.setSubItems(subItemResponses);
            return dto;
        }).collect(Collectors.toList());
    }
}
