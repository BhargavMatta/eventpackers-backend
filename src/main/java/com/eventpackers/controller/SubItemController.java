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

    // ✅ GET ALL SUBITEMS
    @GetMapping("/all")
    public List<SubItem> getAllSubItems() {
        return subItemRepository.findAll();
    }

    // ✅ GET SUBITEMS BY ITEM ID
    @GetMapping("/item/{itemId}")
    public List<SubItem> getSubItemsByItemId(@PathVariable Long itemId) {
        return subItemRepository.findByItemId(itemId);
    }

    // ✅ ADD SUBITEM TO ITEM
    @PostMapping("/add/{itemId}")
    public SubItem addSubItem(@PathVariable Long itemId, @RequestBody SubItem subItem) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        subItem.setItem(item);
        return subItemRepository.save(subItem);
    }

    // ✅ UPDATE SUBITEM
    @PutMapping("/update/{id}")
    public SubItem updateSubItem(@PathVariable Long id, @RequestBody SubItem updatedSubItem) {
        SubItem subItem = subItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubItem not found with ID: " + id));

        subItem.setName(updatedSubItem.getName());
        subItem.setDescription(updatedSubItem.getDescription());
        subItem.setDuration(updatedSubItem.getDuration());
        subItem.setPrice(updatedSubItem.getPrice());

        return subItemRepository.save(subItem);
    }

    // ✅ DELETE SUBITEM
    @DeleteMapping("/delete/{id}")
    public void deleteSubItem(@PathVariable Long id) {
        subItemRepository.deleteById(id);
    }
}
