package com.eventpackers.dto;

import java.util.List;

public class ItemResponse {
    private Long id;
    private String name;
    private List<SubItemResponse> subItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubItemResponse> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<SubItemResponse> subItems) {
        this.subItems = subItems;
    }
}
