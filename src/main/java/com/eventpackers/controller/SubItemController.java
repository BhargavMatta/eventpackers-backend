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

    // ✅ Get all subitems for a specific item
    @GetMapping("/item/{itemId}")
    public List<SubItem> getSubItemsByItemId(@PathVariable Long itemId) {
        return subItemRepo.findByItemId(itemId);
    }

    // ✅ Add a subitem to a specific item
    @PostMapping("/add/{itemId}")
    public SubItem createSubItem(@PathVariable Long itemId, @RequestBody SubItem subItem) {
        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id " + itemId));
        subItem.setItem(item);
        return subItemRepo.save(subItem);
    }

    // ✅ Update a subitem
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

    // ✅ Delete a subitem
    @DeleteMapping("/{id}")
    public void deleteSubItem(@PathVariable Long id) {
        subItemRepo.deleteById(id);
    }
}
