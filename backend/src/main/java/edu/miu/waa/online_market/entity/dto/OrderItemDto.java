package edu.miu.waa.online_market.entity.dto;

import edu.miu.waa.online_market.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderItemDto{
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private OrderStatus orderStatus;
}