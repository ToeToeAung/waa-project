package edu.miu.waa.online_market.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    OrderStatus orderStatus;

    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.orderStatus = OrderStatus.PENDING;
    }

}
