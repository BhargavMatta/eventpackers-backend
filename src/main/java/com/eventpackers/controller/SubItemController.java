package com.eventpackers.controller;

import com.eventpackers.model.SubItem;
import com.eventpackers.model.Item;
import com.eventpackers.repository.SubItemRepository;
import com.eventpackers.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subitems") // ✅ REQUIRED for all endpoints to be prefixed correctly
public class SubItemController {

    @Autowired
    private SubItemRepository subItemRepo;

    @Autowired
    private ItemRepository itemRepo;

    // ✅ Get all subitems by itemId
    @GetMapping("/item/{itemId}")
    public List<SubItem> getByItemId(@PathVariable Long itemId) {
        return subItemRepo.findByItemId(itemId);
    }

    // ✅ Add a new subitem under itemId
    @PostMapping("/add/{itemId}")
    public SubItem add(@PathVariable Long itemId, @RequestBody SubItem subItem) {
        Item item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        subItem.setItem(item);
        return subItemRepo.save(subItem);
    }

    // ✅ Update subitem
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

    // ✅ Delete subitem
    @DeleteMapping("/{id}")
    public void deleteSubItem(@PathVariable Long id) {
        subItemRepo.deleteById(id);
    }
}
