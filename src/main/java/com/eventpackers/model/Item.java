package com.eventpackers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "item_services",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SubItem> subItems;

    // Getters & Setters
}
