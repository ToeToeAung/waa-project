package edu.miu.waa.online_market.entity.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private long id;
    private long productId;
    private int quantity;
}
