package edu.miu.waa.online_market.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.miu.waa.online_market.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "buyer_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
    @JsonManagedReference
    private List<CartItem> cartItems;
    
    public Cart(User user) {
        this.user = user;
        this.cartItems = new ArrayList<>();
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public void removeOrderItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }
}
