package com.eventpackers.controller;

import com.eventpackers.model.Item;
import com.eventpackers.model.SubItem;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.SubItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subitems")  // ✅ This was missing
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

    // ✅ Update SubItem by ID
    @PutMapping("/{subItemId}")
    public SubItem updateSubItem(@PathVariable Long subItemId, @RequestBody SubItem updated) {
        return subItemRepo.findById(subItemId).map(sub -> {
            sub.setName(updated.getName());
            sub.setDescription(updated.getDescription());
            sub.setPrice(updated.getPrice());
            sub.setDuration(updated.getDuration());
            return subItemRepo.save(sub);
        }).orElseThrow(() -> new RuntimeException("SubItem not found"));
    }

    // ✅ Delete SubItem by ID
    @DeleteMapping("/{subItemId}")
    public void deleteSubItem(@PathVariable Long subItemId) {
        subItemRepo.deleteById(subItemId);
    }
}
