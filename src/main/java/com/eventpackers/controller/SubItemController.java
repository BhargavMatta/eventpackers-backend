package com.eventpackers.controller;

import com.eventpackers.model.Item;
import com.eventpackers.model.SubItem;
import com.eventpackers.repository.ItemRepository;
import com.eventpackers.repository.SubItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subitems")
@CrossOrigin(origins = "*")
public class SubItemController {

    @Autowired
    private SubItemRepository subItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    // ✅ Get all subitems
    @GetMapping
    public List<SubItem> getAllSubItems() {
        return subItemRepository.findAll();
    }

    // ✅ Get subitems by itemId
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<SubItem>> getSubItemsByItemId(@PathVariable Long itemId) {
        List<SubItem> subItems = subItemRepository.findByItemId(itemId);
        return ResponseEntity.ok(subItems);
    }

    // ✅ Create subitem
    @PostMapping
    public ResponseEntity<SubItem> createSubItem(@RequestBody SubItem subItem) {
        if (subItem.getItem() == null || subItem.getItem().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Item> itemOpt = itemRepository.findById(subItem.getItem().getId());
        if (!itemOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        subItem.setItem(itemOpt.get());
        SubItem savedSubItem = subItemRepository.save(subItem);
        return ResponseEntity.ok(savedSubItem);
    }

    // ✅ Update subitem
    @PutMapping("/{id}")
    public ResponseEntity<SubItem> updateSubItem(@PathVariable Long id, @RequestBody SubItem updatedSubItem) {
        Optional<SubItem> optionalSubItem = subItemRepository.findById(id);
        if (!optionalSubItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SubItem existing = optionalSubItem.get();
        existing.setName(updatedSubItem.getName());
        existing.setDescription(updatedSubItem.getDescription());
        existing.setDuration(updatedSubItem.getDuration());
        existing.setPrice(updatedSubItem.getPrice());

        return ResponseEntity.ok(subItemRepository.save(existing));
    }

    // ✅ Delete subitem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubItem(@PathVariable Long id) {
        if (!subItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
