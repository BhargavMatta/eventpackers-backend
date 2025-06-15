package com.eventpackers.controller;

import com.eventpackers.model.Item;
import com.eventpackers.model.SubItem;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.SubItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subitems")
public class SubItemController {

    @Autowired
    private SubItemRepository subItemRepo;

    @Autowired
    private ItemRepository itemRepo;

    // Add a SubItem to an Item
    @PostMapping("/add/{itemId}")
    public SubItem addSubItem(@PathVariable Long itemId, @RequestBody SubItem subItem) {
        return itemRepo.findById(itemId).map(item -> {
            subItem.setItem(item);
            return subItemRepo.save(subItem);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // Get all SubItems of a specific Item
    @GetMapping("/item/{itemId}")
    public List<SubItem> getSubItems(@PathVariable Long itemId) {
        return subItemRepo.findByItemId(itemId);
    }

    // Update SubItem by ID
    @PutMapping("/{id}")
    public SubItem updateSubItem(@PathVariable Long id, @RequestBody SubItem updated) {
        return subItemRepo.findById(id).map(sub -> {
            sub.setName(updated.getName());
            sub.setDescription(updated.getDescription());
            sub.setDuration(updated.getDuration());
            sub.setPrice(updated.getPrice());
            return subItemRepo.save(sub);
        }).orElseThrow(() -> new RuntimeException("SubItem not found"));
    }

    // Delete SubItem
    @DeleteMapping("/{id}")
    public void deleteSubItem(@PathVariable Long id) {
        subItemRepo.deleteById(id);
    }
}
