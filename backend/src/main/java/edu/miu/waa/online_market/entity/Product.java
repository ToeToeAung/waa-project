package edu.miu.waa.online_market.entity;

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
    private String description;
    private int quantity;
    private float price;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne()
    @JoinColumn(name = "seller_id")
    User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();


    public Product(String description, int quantity, float price, Category category, User user) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.user = user;
        reviews = new ArrayList<>();
    }

}
