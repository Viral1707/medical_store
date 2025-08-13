package com.medicalstore;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String category;

    @Column(nullable = false)
    private Double price;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Section section;

    public Product(String name, String category, String description, Double price, String imageUrl, Section section) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.section = section;
    }

    public Product() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Section getSection() { return section; }
    public void setSection(Section section) { this.section = section; }
}