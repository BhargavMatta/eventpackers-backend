package com.eventpackers.repository;

import com.eventpackers.model.SubItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubItemRepository extends JpaRepository<SubItem, Long> {
    List<SubItem> findByItemId(Long itemId); // 🔧 this is the missing method
}
