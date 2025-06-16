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
@CrossOrigin(origins = "*")
public class SubItemController {

    @Autowired
    private SubItemRepository subItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    // ✅ Get all SubItems
    @GetMapping("/all")
    public List<SubItem> getAllSubItems() {
        return subItemRepository.findAll();
    }

    // ✅ Get SubItems by Item ID
    @GetMapping("/item/{itemId}")
    public List<SubItem> getSubItemsByItemId(@PathVariable Long itemId) {
        return subItemRepository.findByItemId(itemId);
    }

    // ✅ Add SubItem to an Item
    @PostMapping("/add/{itemId}")
    public SubItem addSubItem(@PathVariable Long itemId, @RequestBody SubItem subItem) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        subItem.setItem(item);
        return subItemRepository.save(subItem);
    }

    // ✅ Update SubItem
    @PutMapping("/update/{subItemId}")
    public SubItem updateSubItem(@PathVariable Long subItemId, @RequestBody SubItem updatedSubItem) {
        SubItem subItem = subItemRepository.findById(subItemId)
                .orElseThrow(() -> new RuntimeException("SubItem not found with ID: " + subItemId));

        subItem.setName(updatedSubItem.getName());
        subItem.setDescription(updatedSubItem.getDescription());
        subItem.setDuration(updatedSubItem.getDuration());
        subItem.setPrice(updatedSubItem.getPrice());

        return subItemRepository.save(subItem);
    }

    // ✅ Delete SubItem
    @DeleteMapping("/delete/{subItemId}")
    public void deleteSubItem(@PathVariable Long subItemId) {
        subItemRepository.deleteById(subItemId);
    }
}
