package edu.miu.waa.online_market.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "buyer_id")
    private User user;

    @OneToMany
    List<CartItem> cartItems;
    
    public Cart(User user) {
        this.user = user;
        this.cartItems = new ArrayList<>();
    }
}
