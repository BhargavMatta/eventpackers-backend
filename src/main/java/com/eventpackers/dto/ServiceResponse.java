package com.eventpackers.dto;

import java.util.List;

public class ServiceResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private List<ItemResponse> items;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<ItemResponse> getItems() { return items; }
    public void setItems(List<ItemResponse> items) { this.items = items; }
}
