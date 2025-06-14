package com.eventpackers.controller;

import com.eventpackers.model.Item;
import com.eventpackers.model.SubItem;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.SubItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subitems") // ✅ Base path to fix 404 errors
public class SubItemController {

    @Autowired
    private SubItemRepository subItemRepo;

    @Autowired
    private ItemRepository itemRepo;

    // ✅ Add a SubItem to a specific Item
    @PostMapping("/add/{itemId}")
    public SubItem addSubItem(@PathVariable Long itemId, @RequestBody SubItem subItem) {
        return itemRepo.findById(itemId).map(item -> {
            subItem.setItem(item);
            return subItemRepo.save(subItem);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // ✅ Get all SubItems of a specific Item
    @GetMapping("/item/{itemId}")
    public List<SubItem> getSubItemsByItem(@PathVariable Long itemId) {
        return subItemRepo.findByItemId(itemId);
    }

    // ✅ Update a SubItem by its ID
    @PutMapping("/{id}")
    public SubItem updateSubItem(@PathVariable Long id, @RequestBody SubItem updated) {
        return subItemRepo.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setPrice(updated.getPrice());
            existing.setDuration(updated.getDuration());
            return subItemRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("SubItem not found"));
    }

    // ✅ Delete a SubItem by its ID
    @DeleteMapping("/{id}")
    public void deleteSubItem(@PathVariable Long id) {
        subItemRepo.deleteById(id);
    }
}
