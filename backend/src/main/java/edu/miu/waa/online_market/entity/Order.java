package edu.miu.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;

    @OneToMany
    private List<OrderItem> orderItems;

    public Order(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        orderItems = new ArrayList<>();
    }

}
