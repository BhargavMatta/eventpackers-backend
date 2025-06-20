package com.eventpackers.repository;

import com.eventpackers.model.SubItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubItemRepository extends JpaRepository<SubItem, Long> {
    List<SubItem> findByItemId(Long itemId);
}
