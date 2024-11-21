package edu.miu.waa.online_market.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private float price;
    private float ratingSum = 0.0f;
    private float totalUser = 0.0f;
    private float overAllRating = 0.0f;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne()
    @JoinColumn(name = "seller_id")
    User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    @JsonManagedReference
    private List<Review> reviews;


    public Product(String description, int quantity, float price, Category category, User user) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.user = user;
        this.reviews = new ArrayList<>();
    }

}
